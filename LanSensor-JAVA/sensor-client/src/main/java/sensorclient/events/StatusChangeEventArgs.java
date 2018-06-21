package sensorclient.events;

import sensorclient.SensorClientStatus;

public class StatusChangeEventArgs extends AbstractSensorEventArgs {
    private SensorClientStatus _oldStatus;
    private SensorClientStatus _newStatus;
    private String _reason;

    public StatusChangeEventArgs(SensorClientStatus oldStatus, SensorClientStatus newStatus, String reason) {
        _oldStatus = oldStatus;
        _newStatus = newStatus;
        _reason = reason;
    }

    public SensorClientStatus getOldStatus() {
        return _oldStatus;
    }

    public SensorClientStatus getNewStatus() {
        return _newStatus;
    }

    public String getReason() {
        return _reason;
    }
}
