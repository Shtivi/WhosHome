package whosHome.common.sensors.events;

import whosHome.common.events.AbstractEventArgs;
import whosHome.common.sensors.IdentificationData;

public class ActivityDetectionEventArgs<T extends IdentificationData> extends AbstractEventArgs {
    public enum Type {
        IN,
        OUT
    }

    private Type _eventType;
    private T _identificationdata;

    public ActivityDetectionEventArgs(Type eventType, T entity) {
        super();

        _eventType = eventType;
        _identificationdata = entity;
    }

    public Type activityType() {
        return _eventType;
    }

    public T getIdentificationData() {
        return _identificationdata;
    }
}
