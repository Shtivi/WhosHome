package whosHome.whosHomeApp.engine;

import com.google.inject.Inject;
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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@Scope(value = "singleton")
public class WhosHomeEngine {
    public enum Status { INITIALIZING, STANDBY, CONNECTING, WORKING }

    private Logger _logger;
    private Status _engineStatus;
    private PeopleRecognitionManager _recognitionMgr;
    private ISensorConnectionsFactory _connectionsFactory;
    private List<ISensorConnection> _sensorConnections;
    private Map<Person, PersonPresenceData> _knownPeople;

    private Event<ErrorEventArgs> onSensorErrorEvent;
    private Event<StatusChangeEventArgs> onSensorStatusChangedEvent;
    private Event<PersonActivityEventArgs> onActivityDetectedEvent;
    private Event<EngineStatusChangedEventArgs> onEngineStatusChangedEvent;

    @Autowired
    public WhosHomeEngine(PeopleRecognitionManager recognitionManager, ISensorConnectionsFactory connectionsFactory) {
        _logger = Logger.getLogger("WhosHomeEngine");
        _connectionsFactory = connectionsFactory;
        _recognitionMgr = recognitionManager;
        _knownPeople = new ConcurrentHashMap<>();

        onActivityDetectedEvent = new Event<>();
        onSensorErrorEvent = new Event<>();
        onSensorStatusChangedEvent = new Event<>();
        onEngineStatusChangedEvent = new Event<>();
    }

    public void start() throws WhosHomeException {
        setEngineStatus(Status.CONNECTING, "connecting to sensor services");
        _sensorConnections
                .stream()
                .filter(connection -> connection.getConnectionMetadata().isActiveDefaultly())
                .forEach(connection -> {
                    connection.listen(new SensorListener(connection));
                    connection.connect();
                });
        this.setEngineStatus(Status.WORKING, "started according to user request");
    }

    public void stop() {
        _sensorConnections.stream()
                .filter(connection -> connection.getStatus().equals(SensorConnectionState.CONNECTED))
                .forEach(connection -> connection.disconnect());
        setEngineStatus(Status.STANDBY, "stopped according to user request");
    }

    public Iterable<ISensorConnection> getAllSensorConnections() throws WhosHomeEngineException {
        if (_sensorConnections == null) {
            throw new WhosHomeEngineException("sensor connections was not initialized yet");
        }
        return _sensorConnections;
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

    public void initialize() {
        setEngineStatus(Status.INITIALIZING, "starting engine initialization");
        _sensorConnections = _connectionsFactory.createAllConnections();
        setEngineStatus(Status.STANDBY, "finished initialization");
    }

    private void setEngineStatus(Status newStatus, Throwable error) {
        Status oldStatus = _engineStatus;
        _engineStatus = newStatus;
        EngineStatusChangedEventArgs args = new EngineStatusChangedEventArgs.Builder(oldStatus, newStatus)
                .withReason(error.getMessage())
                .withError(error)
                .build();
        this.onEngineStatusChangedEvent.dispatch(args);
    }

    private void setEngineStatus(Status newStatus, String reason) {
        Status oldStatus = _engineStatus;
        _engineStatus = newStatus;
        EngineStatusChangedEventArgs eventArgs = new EngineStatusChangedEventArgs.Builder(oldStatus, newStatus)
                .withReason(reason)
                .build();
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
                    new PersonActivityEventArgs(activityDetails, person, personPresenceData.presenceCertainty());
            this.onActivityDetectedEvent.dispatch(personActivityEventArgs);
        });
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
            onSensorErrorEvent.dispatch(args);
        }

        @Override
        public void onStatusChange(StatusChangeEventArgs args) {
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
