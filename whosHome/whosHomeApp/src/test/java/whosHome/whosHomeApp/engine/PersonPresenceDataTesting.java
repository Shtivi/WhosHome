package whosHome.whosHomeApp.engine;

import org.junit.Before;
import org.junit.Test;
import whosHome.common.models.SensorConnectionMetadata;
import whosHome.common.sensors.client.events.ActivityDetectionEventArgs;
import whosHome.whosHomeApp.mocks.IdentificationDataMock;
import whosHome.whosHomeApp.mocks.RecognitionManagerMock;
import whosHome.whosHomeApp.mocks.SensorConnectionsFactoryMock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class PersonPresenceDataTesting {
    private PersonPresenceData personPresenceData;

    @Before
    public void arrangments() {
        personPresenceData = PersonPresenceData.of(RecognitionManagerMock.P_1);
    }

    @Test
    public void presenceChances_noActivities_shouldReturnZero() {
        assertThat(personPresenceData.presenceChances(), equalTo(0.0));
    }

    @Test
    public void presenceChances_onlyOneInActivity_shouldReturnPositiveResult() {
        final int sensorConnectionMetadataID = 1;
        SensorConnectionMetadata connectionMetadata = SensorConnectionsFactoryMock.connectionsMetadata.get(sensorConnectionMetadataID);
        personPresenceData.addActivity(inEventArgs("1", sensorConnectionMetadataID));

        assertThat(personPresenceData.presenceChances(), equalTo((double)connectionMetadata.getSensorTypeMetadata().getReliability()));
    }

    @Test
    public void presenceChances_twoInActivitiesFromSameSensorType_shouldReturnAverage() {
        final int sensorConnectionMetadataID = 1;
        SensorConnectionMetadata connectionMetadata = SensorConnectionsFactoryMock.connectionsMetadata.get(sensorConnectionMetadataID);
        personPresenceData.addActivity(inEventArgs("1", sensorConnectionMetadataID));
        personPresenceData.addActivity(inEventArgs("1", sensorConnectionMetadataID));

        assertThat(personPresenceData.presenceChances(), equalTo((double)connectionMetadata.getSensorTypeMetadata().getReliability()));
    }

    @Test
    public void presenceChances_twoInActivitiesDifferentSensor_shouldReturnAverage() {
        // Arrange
        final int connID_1 = 1;
        final int connID_2 = 2;

        SensorConnectionMetadata conn_1 = SensorConnectionsFactoryMock.connectionsMetadata.get(connID_1);
        SensorConnectionMetadata conn_2 = SensorConnectionsFactoryMock.connectionsMetadata.get(connID_2);

        double expectedChances = ((double)conn_1.getSensorTypeMetadata().getReliability() +
                                            conn_2.getSensorTypeMetadata().getReliability()) / 2;

        // Act
        personPresenceData.addActivity(inEventArgs("1", connID_1));
        personPresenceData.addActivity(inEventArgs("1", connID_2));

        // Assert
        assertThat(personPresenceData.presenceChances(), equalTo(expectedChances));
    }

    @Test
    public void presenceChances_inAndOutActivities_shouldReturnBalancedResult() {
        final int sensorConnectionMetadataID = 1;
        SensorConnectionMetadata connectionMetadata = SensorConnectionsFactoryMock.connectionsMetadata.get(sensorConnectionMetadataID);
        personPresenceData.addActivity(inEventArgs("1", sensorConnectionMetadataID));
        personPresenceData.addActivity(outEventArgs("1", sensorConnectionMetadataID));

        assertThat(personPresenceData.presenceChances(), equalTo(0.0));
    }

    @Test
    public void presenceChances_multipleVariantActivities_shouldReturnBalancedResult() throws Exception {
        // Arrange
        List<ActivityDetectionEventArgs> activities = new ArrayList<>();
        IntStream.range(1, 3).forEach(i -> activities.add(inEventArgs("1", i)));
        IntStream.range(2, 3).forEach(i -> activities.add(outEventArgs("1", i)));

        double expectedChances = activities
                .stream()
                .mapToDouble(args -> args.activityType().equals(ActivityDetectionEventArgs.Type.IN) ? args.getConnectionMetadata().getSensorTypeMetadata().getReliability() : -args.getConnectionMetadata().getSensorTypeMetadata().getReliability())
                .average()
                .orElseThrow(() -> new Exception("No activities to work with"));

        // Act
        activities.forEach(args -> personPresenceData.addActivity(args));

        // Assert
        assertThat(personPresenceData.presenceChances(), equalTo(expectedChances));
    }

    private ActivityDetectionEventArgs<IdentificationDataMock> inEventArgs(String id, int connID) {
        return new ActivityDetectionEventArgs<>(ActivityDetectionEventArgs.Type.IN,
                new IdentificationDataMock(id),
                SensorConnectionsFactoryMock.connectionsMetadata.get(connID));
    }

    private ActivityDetectionEventArgs<IdentificationDataMock> outEventArgs(String id, int connID) {
        return new ActivityDetectionEventArgs<>(ActivityDetectionEventArgs.Type.OUT,
                new IdentificationDataMock(id),
                SensorConnectionsFactoryMock.connectionsMetadata.get(connID));
    }
}
