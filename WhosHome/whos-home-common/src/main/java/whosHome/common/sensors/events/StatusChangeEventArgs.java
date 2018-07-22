package whosHome.common.sensors.events;

import whosHome.common.events.AbstractEventArgs;
import whosHome.common.sensors.SensorConnectionState;

public class StatusChangeEventArgs extends AbstractEventArgs {
    private SensorConnectionState _oldStatus;
    private SensorConnectionState _newStatus;
    private String _reason;

    public StatusChangeEventArgs(SensorConnectionState oldStatus, SensorConnectionState newStatus, String reason) {
        _oldStatus = oldStatus;
        _newStatus = newStatus;
        _reason = reason;
    }

    public SensorConnectionState getOldStatus() {
        return _oldStatus;
    }

    public SensorConnectionState getNewStatus() {
        return _newStatus;
    }

    public String getReason() {
        return _reason;
    }
}
