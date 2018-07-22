package whosHome.whosHomeApp.engine.sensors;

import sensorclient.entities.LanEntity;
import whosHome.common.dataProviders.ISensorConnectionsMetadataDao;
import whosHome.common.models.SensorConnectionMetadata;
import whosHome.common.sensors.ISensorConnection;
import whosHome.common.sensors.SensorConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SensorConnectionsFactory implements ISensorConnectionsFactory {
    private static final int LAN_SENSOR_TYPE_ID = 1;

    private Map<Integer, Function<SensorConnectionMetadata, ISensorConnection>> _builders;
    private ISensorConnectionsMetadataDao _connectionsMetadataDao;

    public SensorConnectionsFactory(ISensorConnectionsMetadataDao connectionsMetadataDao) {
        _builders = new HashMap<>();
        _connectionsMetadataDao = connectionsMetadataDao;

        fillBuilders();
    }

    @Override
    public ISensorConnection createConnection(int sensorConnectionID) {
        return null;
    }

    @Override
    public List<ISensorConnection> createAllConnectionS() {
        return null;
    }

    private void fillBuilders() {
        _builders.put(1, (connectionMetadata -> new SensorConnection<LanEntity>(connectionMetadata)));
    }

    private ISensorConnection buildSensorConnectionInstance(SensorConnectionMetadata metadata) {
        Function<SensorConnectionMetadata, ISensorConnection> builder = _builders.get(metadata.getSensorTypeMetadata().getID());
        return builder.apply(metadata);
    }
}
