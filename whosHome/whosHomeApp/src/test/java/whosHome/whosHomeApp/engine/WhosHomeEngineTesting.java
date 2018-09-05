package whosHome.whosHomeApp.engine;

import net.jodah.concurrentunit.Waiter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import whosHome.common.exceptions.WhosHomeException;
import whosHome.common.sensors.client.ISensorConnection;
import whosHome.common.sensors.client.SensorConnectionState;
import whosHome.common.sensors.client.commands.SensorCommand;
import whosHome.common.sensors.client.events.ActivityDetectionEventArgs;
import whosHome.whosHomeApp.engine.events.PersonActivityEventArgs;
import whosHome.whosHomeApp.engine.recognition.PeopleRecognitionManager;
import whosHome.whosHomeApp.mocks.IdentificationDataMock;
import whosHome.whosHomeApp.mocks.RecognitionManagerMock;
import static whosHome.whosHomeApp.mocks.RecognitionManagerMock.*;
import whosHome.whosHomeApp.mocks.SensorConnectionsFactoryMock;
import whosHome.whosHomeApp.models.Person;

import java.util.*;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class WhosHomeEngineTesting {
    PeopleRecognitionManager recognitionManager;
    SensorConnectionsFactoryMock sensorConnectionsFactory;
    WhosHomeEngine engine;
    Waiter waiter;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void arrange() {
        waiter = new Waiter();
        recognitionManager = RecognitionManagerMock.createMock();
        sensorConnectionsFactory = new SensorConnectionsFactoryMock();
        engine = new WhosHomeEngine(recognitionManager, sensorConnectionsFactory);
    }

    @Test
    public void initialize_normalState_shouldFinishInitialization() throws TimeoutException {
        // Arrange
        engine.onEngineStatusChanged().once((eventArgs) -> {
            // Assert
            assertThat(eventArgs.getNewStatus(), equalTo(WhosHomeEngine.Status.INITIALIZED));
            assertFalse(eventArgs.getError().isPresent());
            waiter.resume();
        });

        // Act
        engine.initialize();

        waiter.await(500);
    }

    @Test
    public void initialize_normalState_shouldListenAllSensors() throws TimeoutException {
        // Arrange
        sensorConnectionsFactory.connections.forEach((id, connection) -> {
            sensorConnectionsFactory.connections.put(id, spy(connection));
        });

        engine.onEngineStatusChanged().once(eventArg -> {
            // Assert
            for (ISensorConnection mockedConnection : sensorConnectionsFactory.connections.values())
                verify(mockedConnection, times(1)).listen(any());
            waiter.resume();
        });

        // Act
        engine.initialize();

        waiter.await(500);
    }

    @Test
    public void start_normalState_shouldConnectToAllDefaultlyActiveSensors() throws TimeoutException {
        // Arrange
        sensorConnectionsFactory.connections.forEach((id, connection) -> {
            sensorConnectionsFactory.connections.put(id, spy(connection));
        });

        engine.initialize().onEngineStatusChanged().once(eventArg -> {
            // Assert
            sensorConnectionsFactory.connections.values().stream()
                    .filter(connection -> connection.getConnectionMetadata().isActiveDefaultly())
                    .forEach(connection -> verify(connection, times(1)).connect());
            waiter.resume();
        });

        // Act
        engine.start();

        waiter.await(500);
    }

    @Test
    public void start_normalState_shouldChangeStatusToWorking() throws TimeoutException {
        // Arrange
        engine.initialize();

        engine.onEngineStatusChanged().once((eventArgs) -> {
            // Assert
            assertThat(eventArgs.getNewStatus(), equalTo(WhosHomeEngine.Status.WORKING));
            assertFalse(eventArgs.getError().isPresent());
            waiter.resume();
        });

        engine.start();

        waiter.await(500);
    }

    @Test
    public void start_notInitialized_shouldThrowException() {
        thrown.expect(WhosHomeException.class);
        engine.start();
    }

    @Test
    public void stop_notWorking_shouldThrowException() {
        thrown.expect(WhosHomeException.class);
        engine.stop();
    }

    @Test
    public void stop_normalState_shouldDisconnectAllConnectedSensors() throws TimeoutException {
        // Arrange
        sensorConnectionsFactory.connections.forEach((id, connection) -> {
            sensorConnectionsFactory.connections.put(id, spy(connection));
        });

        engine.initialize().start().onEngineStatusChanged().once(eventArg -> {
            // Assert
            sensorConnectionsFactory.connections.values().stream()
                    .filter(connection -> connection.getStatus().equals(SensorConnectionState.CONNECTED))
                    .forEach(connection -> verify(connection, times(1)).disconnect());
            waiter.resume();
        });

        // Act
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                engine.stop();
            }
        }, 100);

        waiter.await(5000);
    }

    @Test
    public void stop_normalState_shouldChangeStatusToInitialized() throws TimeoutException {
        // Arrange
        engine.initialize().start().onEngineStatusChanged().once((eventArgs) -> {
            // Assert
            assertThat(eventArgs.getNewStatus(), equalTo(WhosHomeEngine.Status.INITIALIZED));
            assertFalse(eventArgs.getError().isPresent());
            waiter.resume();
        });

        engine.stop();

        waiter.await(500);
    }

    @Test
    public void onActivityDetection_singleActivityAndRecognized_shouldDispatchEventNormally() throws TimeoutException {
        // Arrange
        Person person = RecognitionManagerMock.P_1;
        ActivityDetectionEventArgs<IdentificationDataMock> args = inEventArgs(person.getID(), 1);
        SensorCommand<ActivityDetectionEventArgs<IdentificationDataMock>> cmd = SensorCommand.of("dispatchDetection", args);

        engine.initialize().start().onActivityDetection().once((eventArg) -> {
            assertThat(eventArg.getSubject(), equalTo(person));
            assertThat(eventArg.getActivityDetails(), equalTo(args));
            waiter.resume();
        });

        // Act
        sensorConnectionsFactory.connections.get(1).sendCommand(cmd);

        waiter.await(500);
    }

    @Test
    public void onActivityDetection_multipleActivitiesAndRecognized_shouldDispatchEventsNormally() throws TimeoutException {
        // Arrange
        List<ActivityDetectionEventArgs<IdentificationDataMock>> eventArgs = Arrays.asList(
                inEventArgs(P_1.getID(), 1),
                inEventArgs(P_1.getID(), 2),
                inEventArgs(P_2.getID(), 3),
                outEventArgs(P_2.getID(), 3));

        List<PersonActivityEventArgs> capturedEventArgs = new ArrayList<>();

        engine.initialize().start().onActivityDetection().listen((args) -> {
            Optional<ActivityDetectionEventArgs<IdentificationDataMock>> matchedEventArg = eventArgs
                    .stream()
                    .filter(detectionArgs -> args.getActivityDetails().equals(detectionArgs))
                    .findFirst();
            assertThat(matchedEventArg.isPresent(), is(true));
            capturedEventArgs.add(args);

            waiter.resume();
        });

        // Act
        ISensorConnection sensor = sensorConnectionsFactory.connections.get(1);
        eventArgs.forEach(args -> sensor.sendCommand(SensorCommand.of("dispatchDetection", args)));

        waiter.await(500);

        // Assert
        assertThat(capturedEventArgs.size(), equalTo(eventArgs.size()));
    }

    private ActivityDetectionEventArgs<IdentificationDataMock> inEventArgs(String id, int connID) {
        return new ActivityDetectionEventArgs<>(ActivityDetectionEventArgs.Type.IN,
                new IdentificationDataMock(id),
                this.sensorConnectionsFactory.connections.get(connID).getConnectionMetadata());
    }

    private ActivityDetectionEventArgs<IdentificationDataMock> outEventArgs(String id, int connID) {
        return new ActivityDetectionEventArgs<>(ActivityDetectionEventArgs.Type.OUT,
                new IdentificationDataMock(id),
                this.sensorConnectionsFactory.connections.get(connID).getConnectionMetadata());
    }
}
