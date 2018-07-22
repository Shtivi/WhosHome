package whosHome.whosHomeApp.engine.sensors;

import whosHome.common.sensors.ISensorConnection;

import java.util.List;

public interface ISensorConnectionsFactory {
    ISensorConnection createConnection(int sensorConnectionID);
    List<ISensorConnection> createAllConnectionS();
}
