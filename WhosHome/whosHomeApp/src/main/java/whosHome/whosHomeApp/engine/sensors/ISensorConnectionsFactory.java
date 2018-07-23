package whosHome.whosHomeApp.engine.sensors;

import whosHome.common.exceptions.WhosHomeException;
import whosHome.common.sensors.ISensorConnection;
import whosHome.whosHomeApp.engine.sensors.builders.SensorConnectionInstatiationException;

import java.util.List;

public interface ISensorConnectionsFactory {
    ISensorConnection createConnection(int sensorConnectionID) throws SensorConnectionInstatiationException;
    List<ISensorConnection> createAllConnectionS() throws SensorConnectionInstatiationException;
}
