package sensorclient;

import sensorclient.commands.SensorCommand;

public interface ISensorClient {
    /**
     * Connects the service, if not connected already and if possible (status == SensorClientStatus.AVAILABLE).
     */
    void connect();

    /**
     * Connects the service, if not connected already and if possible (status == SensorClientStatus.AVAILABLE).
     * You should disconnect the client when the program shut down.
     */
    void disconnect();

    /**
     * Returns the current client status.
     * @return an SensorClientStatus enum object describing the current client status.
     */
    SensorClientStatus getStatus();

    /** Sends a command to the sensor
     *
     * @param command - Command object to send & execute
     */
    void sendCommand(SensorCommand command);

    /** Registers a listener to the sensor
     *
     * @param listener - An object implements the ISensorListener interface
     */
    boolean listen(ISensorListener listener);

    /** Removes a listener
     *
     * @param listener - An object implements ISensorListener object to remove
     */
    boolean removeListener(ISensorListener listener);
}
