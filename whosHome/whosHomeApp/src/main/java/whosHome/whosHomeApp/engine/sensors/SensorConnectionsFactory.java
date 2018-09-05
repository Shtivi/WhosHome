package whosHome.whosHomeApp.engine.sensors;

import com.google.inject.Inject;
import whosHome.common.dataProviders.ISensorConnectionsMetadataDao;
import whosHome.common.models.SensorConnectionMetadata;
import whosHome.common.sensors.client.ISensorConnection;
import whosHome.whosHomeApp.engine.sensors.builders.LanSensorConnectionBuilder;
import whosHome.whosHomeApp.engine.sensors.builders.SensorConnectionInstatiationException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SensorConnectionsFactory implements ISensorConnectionsFactory {
    private static final String NO_SUCH_CONNECTION = "Could not find a data record for connection with id %d";
    private static final String NO_BUILDER_SPECIFIED = "No builder specified for connection type '%s' (%d)";

    private Map<Integer, Function<SensorConnectionMetadata, ISensorConnection>> _builders;
    private ISensorConnectionsMetadataDao _connectionsMetadataDao;

    @Inject
    public SensorConnectionsFactory(ISensorConnectionsMetadataDao connectionsMetadataDao) {
        _builders = new HashMap<>();
        _connectionsMetadataDao = connectionsMetadataDao;

        fillBuilders();
    }

    @Override
    public ISensorConnection createConnection(int sensorConnectionID) throws SensorConnectionInstatiationException {
        SensorConnectionMetadata sensorConnectionMetadata =
                _connectionsMetadataDao
                        .fetchById(sensorConnectionID)
                        .orElseThrow(() -> new SensorConnectionInstatiationException(String.format(NO_SUCH_CONNECTION, sensorConnectionID)));
        return buildSensorConnectionInstance(sensorConnectionMetadata);
    }

    @Override
    public List<ISensorConnection> createAllConnections() throws SensorConnectionInstatiationException {
        Collection<SensorConnectionMetadata> connectionsMetadata = _connectionsMetadataDao.fetchAll();
        List<ISensorConnection> sensorConnectionInstances = connectionsMetadata.stream()
                .map(this::buildSensorConnectionInstance)
                .collect(Collectors.toList());
        return sensorConnectionInstances;
    }

    private void fillBuilders() {
        _builders.put(1, new LanSensorConnectionBuilder());
    }

    private ISensorConnection buildSensorConnectionInstance(SensorConnectionMetadata metadata) {
        Function<SensorConnectionMetadata, ISensorConnection> builder = _builders.get(metadata.getSensorTypeMetadata().getID());
        if (builder == null) {
            throw new SensorConnectionInstatiationException(String.format(NO_BUILDER_SPECIFIED, metadata.getSensorTypeMetadata().getTitle(), metadata.getSensorTypeMetadata().getID()));
        }
        return builder.apply(metadata);
    }
}
