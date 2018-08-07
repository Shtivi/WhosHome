package whosHome.whosHomeApp.engine.sensors;

import whosHome.common.sensors.client.ISensorConnection;
import whosHome.whosHomeApp.engine.sensors.builders.SensorConnectionInstatiationException;

import java.util.List;

public interface ISensorConnectionsFactory {
    ISensorConnection createConnection(int sensorConnectionID) throws SensorConnectionInstatiationException;
    List<ISensorConnection> createAllConnections() throws SensorConnectionInstatiationException;
}
