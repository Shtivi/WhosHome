package whosHome.common.sensors.client.events;

import whosHome.common.events.AbstractEventArgs;
import whosHome.common.models.SensorConnectionMetadata;
import whosHome.common.sensors.client.IdentificationData;

public class ActivityDetectionEventArgs<T extends IdentificationData> extends AbstractEventArgs {
    public enum Type {
        IN,
        OUT
    }

    private Type _eventType;
    private T _identificationdata;
    private SensorConnectionMetadata _connectionMetadata;

    public ActivityDetectionEventArgs(Type eventType, T entity, SensorConnectionMetadata connectionMetadata) {
        super();

        _eventType = eventType;
        _identificationdata = entity;
        _connectionMetadata = connectionMetadata;
    }

    public Type activityType() {
        return _eventType;
    }

    public T getIdentificationData() {
        return _identificationdata;
    }

    public SensorConnectionMetadata getConnectionMetadata() {
        return _connectionMetadata;
    }
}
