package whosHome.common.sensors.client.events;

import whosHome.common.events.AbstractEventArgs;
import whosHome.common.models.SensorConnectionMetadata;
import whosHome.common.sensors.client.SensorConnectionState;

public class StatusChangeEventArgs extends AbstractEventArgs {
    private SensorConnectionState _oldStatus;
    private SensorConnectionState _newStatus;
    private String _reason;
    private SensorConnectionMetadata _connectionMetadata;

    public StatusChangeEventArgs(SensorConnectionState oldStatus, SensorConnectionState newStatus, String reason, SensorConnectionMetadata connectionMetadata) {
        _oldStatus = oldStatus;
        _newStatus = newStatus;
        _reason = reason;
        _connectionMetadata = connectionMetadata;
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

    public SensorConnectionMetadata getSensorConnectionMetadata() {
        return _connectionMetadata;
    }
}
