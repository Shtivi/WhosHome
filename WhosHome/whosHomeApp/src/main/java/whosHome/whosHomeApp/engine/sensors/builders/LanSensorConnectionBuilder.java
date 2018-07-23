package whosHome.whosHomeApp.engine.sensors.builders;

import sensorclient.entities.LanEntity;
import whosHome.common.models.SensorConnectionMetadata;
import whosHome.common.sensors.ISensorConnection;
import whosHome.common.sensors.SensorConnection;

import java.util.function.Function;

public class LanSensorConnectionBuilder implements Function<SensorConnectionMetadata, ISensorConnection> {
    @Override
    public ISensorConnection apply(SensorConnectionMetadata connectionMetadata) {
        return new SensorConnection<LanEntity>(connectionMetadata);
    }
}
