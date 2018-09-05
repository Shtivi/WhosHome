package whosHome.whosHomeApp.engine;

import com.google.inject.Singleton;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import whosHome.common.events.Event;
import whosHome.common.exceptions.WhosHomeException;
import whosHome.common.sensors.client.ISensorConnection;
import whosHome.common.sensors.client.ISensorListener;
import whosHome.common.sensors.client.IdentificationData;
import whosHome.common.sensors.client.SensorConnectionState;
import whosHome.common.sensors.client.events.ActivityDetectionEventArgs;
import whosHome.common.sensors.client.events.ErrorEventArgs;
import whosHome.common.sensors.client.events.StatusChangeEventArgs;
import whosHome.whosHomeApp.engine.errors.WhosHomeEngineException;
import whosHome.whosHomeApp.engine.events.EngineStatusChangedEventArgs;
import whosHome.whosHomeApp.engine.events.PersonActivityEventArgs;
import whosHome.whosHomeApp.engine.recognition.PeopleRecognitionManager;
import whosHome.whosHomeApp.engine.sensors.ISensorConnectionsFactory;
import whosHome.whosHomeApp.models.Person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@Scope(value = "singleton")
public class WhosHomeEngine {
    public enum Status { CREATED, INITIALIZED, WORKING }

    private Logger _logger;
    private Status _engineStatus;
    private PeopleRecognitionManager _recognitionMgr;
    private ISensorConnectionsFactory _connectionsFactory;
    private Map<Integer, ISensorConnection> _sensorConnections;
    private Map<Person, PersonPresenceData> _knownPeople;

    private Event<ErrorEventArgs> onSensorErrorEvent;
    private Event<StatusChangeEventArgs> onSensorStatusChangedEvent;
    private Event<PersonActivityEventArgs> onActivityDetectedEvent;
    private Event<EngineStatusChangedEventArgs> onEngineStatusChangedEvent;

    @Autowired
    public WhosHomeEngine(PeopleRecognitionManager recognitionManager, ISensorConnectionsFactory connectionsFactory) {
        _logger = Logger.getLogger("WhosHomeEngine");
        _logger.debug("creating WhosHomeEngine object");

        _connectionsFactory = connectionsFactory;
        _recognitionMgr = recognitionManager;
        _knownPeople = new ConcurrentHashMap<>();
        _sensorConnections = new HashMap<>();

        onActivityDetectedEvent = new Event<>();
        onSensorErrorEvent = new Event<>();
        onSensorStatusChangedEvent = new Event<>();
        onEngineStatusChangedEvent = new Event<>();

        _logger.debug("engine created");
        _engineStatus = Status.CREATED;
    }

    public synchronized WhosHomeEngine start() throws WhosHomeException {
        if (_engineStatus != Status.INITIALIZED) {
            throw new WhosHomeEngineException("unable to start, engine is not initialized").withHttpStatus(400);
        }

        _logger.info("connecting to sensors");
        _sensorConnections
                .values()
                .stream()
                .filter(connection -> connection.getConnectionMetadata().isActiveDefaultly())
                .forEach(connection -> connection.connect());

        this.setEngineStatus(Status.WORKING, "started according to user request");
        _logger.info("finished connecting to sensors");

        return this;
    }

    public synchronized WhosHomeEngine initialize() {
        _logger.info("fetching sensor connection details");
        List<ISensorConnection> allConnections = _connectionsFactory.createAllConnections();
        allConnections.forEach(connection -> {
            connection.listen(new SensorListener(connection));
            _sensorConnections.put(connection.getConnectionMetadata().getID(), connection);
        });
        setEngineStatus(Status.INITIALIZED, "finished initialization");
        return this;
    }

    public synchronized WhosHomeEngine stop() throws WhosHomeEngineException {
        if (_engineStatus != Status.WORKING) {
            throw new WhosHomeEngineException("unable to stop, engine is not working").withHttpStatus(400);
        }

        _logger.info("disconnecting from sensors");
        _sensorConnections.values().stream()
                .filter(connection -> connection.getStatus().equals(SensorConnectionState.CONNECTED))
                .forEach(connection -> connection.disconnect());
        setEngineStatus(Status.INITIALIZED, "stopped according to user request");
        _logger.info("disconnected from sensors");

        return this;
    }

    public synchronized Iterable<ISensorConnection> getAllSensorConnections() throws WhosHomeEngineException {
        if (_sensorConnections == null || _sensorConnections.isEmpty()) {
            throw new WhosHomeEngineException("sensor connections was not initialized yet");
        }
        return _sensorConnections.values();
    }

    public synchronized Optional<ISensorConnection> getSensorConnection(int sensorConnectionID) {
        return Optional.ofNullable(_sensorConnections.get(sensorConnectionID));
    }

    public Iterable<PersonPresenceData> getPeoplePresenceData() {
        return _knownPeople.values();
    }

    public Status getEngineStatus() {
        return _engineStatus;
    }

    public Event<ErrorEventArgs> onSensorError() {
        return onSensorErrorEvent;
    }

    public Event<StatusChangeEventArgs> onSensorStatusChanged() {
        return onSensorStatusChangedEvent;
    }

    public Event<PersonActivityEventArgs> onActivityDetection() {
        return onActivityDetectedEvent;
    }

    public Event<EngineStatusChangedEventArgs> onEngineStatusChanged() {
        return onEngineStatusChangedEvent;
    }

    private void setEngineStatus(Status newStatus, Throwable error) {
        Status oldStatus = _engineStatus;
        _engineStatus = newStatus;
        EngineStatusChangedEventArgs args = new EngineStatusChangedEventArgs.Builder(oldStatus, newStatus)
                .withReason(error.getMessage())
                .withError(error)
                .build();
        _logger.info(String.format("engine status changed from '%s' to '%s' with exception '%s': '%s'", oldStatus.toString(), newStatus.toString(), error.getClass().getName(), error.getMessage()));
        this.onEngineStatusChangedEvent.dispatch(args);
    }

    private void setEngineStatus(Status newStatus, String reason) {
        Status oldStatus = _engineStatus;
        _engineStatus = newStatus;
        EngineStatusChangedEventArgs eventArgs = new EngineStatusChangedEventArgs.Builder(oldStatus, newStatus)
                .withReason(reason)
                .build();
        _logger.info(String.format("engine status changed from '%s' to '%s': '%s'", oldStatus.toString(), newStatus.toString(), reason == null ? "no reason specified" : reason));
        this.onEngineStatusChangedEvent.dispatch(eventArgs);
    }

    private void activityDetected(ActivityDetectionEventArgs activityDetails) {
        IdentificationData identificationData = activityDetails.getIdentificationData();
        Optional<Person> recognizedPerson = _recognitionMgr.recognize(identificationData);
        recognizedPerson.ifPresent(person -> {
            PersonPresenceData personPresenceData;
            if (!this._knownPeople.containsKey(person)) {
                personPresenceData = addNewPersonData(activityDetails, person);
            } else {
                personPresenceData = this._knownPeople.get(person).addActivity(activityDetails);
            }

            PersonActivityEventArgs personActivityEventArgs =
                    new PersonActivityEventArgs(activityDetails, person, personPresenceData.presenceChances());
            this.onActivityDetectedEvent.dispatch(personActivityEventArgs);
        });
        // TODO: 9/2/2018 what happens with un recognized entities?
    }

    private PersonPresenceData addNewPersonData(ActivityDetectionEventArgs activityDetails, Person person) {
        PersonPresenceData personPresenceData = PersonPresenceData.of(person).addActivity(activityDetails);
        this._knownPeople.put(person, personPresenceData);
        return personPresenceData;
    }

    private class SensorListener implements ISensorListener {
        private ISensorConnection _connection;

        public SensorListener(ISensorConnection connection) {
            _connection = connection;
        }

        @Override
        public void onError(ErrorEventArgs args) {
            _logger.warn(String.format("sensor #%d-%s state changed to '%s': %s '%s'",
                    args.getSensorConnectionMetadata().getID(),
                    args.getSensorConnectionMetadata().getName(),
                    _connection.getStatus().toString(),
                    args.getError().getClass().getName(),
                    args.getError().getMessage()));
            onSensorErrorEvent.dispatch(args);
        }

        @Override
        public void onStatusChange(StatusChangeEventArgs args) {
            _logger.info(String.format("sensor #%d-%s state changed from '%s' to '%s': '%s'",
                    args.getSensorConnectionMetadata().getSensorConnectionID(),
                    args.getSensorConnectionMetadata().getName(),
                    args.getOldStatus(),
                    args.getNewStatus(),
                    args.getReason()));
            onSensorStatusChangedEvent.dispatch(args);
        }

        @Override
        public void onActivityDetection(ActivityDetectionEventArgs args) {
            activityDetected(args);
        }

        @Override
        public void onEntitiesFetched(Iterable entities) {
            for (IdentificationData id : (Iterable<IdentificationData>)entities) {
                ActivityDetectionEventArgs<IdentificationData> activityDetails =
                        new ActivityDetectionEventArgs<>(ActivityDetectionEventArgs.Type.IN, id, _connection.getConnectionMetadata());
                activityDetected(activityDetails);
            }
        }
    }
}
