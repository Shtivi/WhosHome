package whosHome.common.sensors;


import whosHome.common.models.SensorConnectionMetadata;
import whosHome.common.sensors.commands.SensorCommand;

/**
 *
 * @param <T>
 */
public interface ISensorConnection<T extends IdentificationData> {
    /**
     * Connects the service, if not connected already and if possible (status == SensorConnectionState.AVAILABLE).
     */
    void connect();

    /**
     * Connects the service, if not connected already and if possible (status == SensorConnectionState.AVAILABLE).
     * You should disconnect the client when the program shut down.
     */
    void disconnect();

    /**
     * Returns the current client status.
     * @return an SensorConnectionState enum object describing the current client status.
     */
    SensorConnectionState getStatus();

    /** Sends a command to the sensor
     *
     * @param command - Command object to send & execute
     */
    void sendCommand(SensorCommand command);

    /** Registers a listener to the sensor
     *
     * @param listener - An object implements the ISensorListener interface
     */
    boolean listen(ISensorListener<T> listener);

    /** Removes a listener
     *
     * @param listener - An object implements ISensorListener object to remove
     */
    boolean removeListener(ISensorListener<T> listener);

    /**
     * Returns the sensor connection metadata
     * @return a SensorConnectionMetadata instance describing the connection details
     */
    SensorConnectionMetadata getConnectionMetadata();
}
