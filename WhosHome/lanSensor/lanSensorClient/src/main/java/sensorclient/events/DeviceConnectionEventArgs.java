package sensorclient.events;

import sensorclient.entities.LanEntity;

public class DeviceConnectionEventArgs extends AbstractSensorEventArgs {
    public enum ActivityEventType {
        IN,
        OUT
    }

    private ActivityEventType _eventType;
    private LanEntity _entity;

    public DeviceConnectionEventArgs(ActivityEventType eventType, LanEntity entity) {
        super();

        _eventType = eventType;
        _entity = entity;
    }

    public ActivityEventType activityType() {
        return _eventType;
    }

    public LanEntity getEntity() {
        return _entity;
    }
}
