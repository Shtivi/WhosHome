package whosHome.common.sensors.client.events;

import whosHome.common.events.AbstractEventArgs;
import whosHome.common.models.SensorConnectionMetadata;

public class ErrorEventArgs extends AbstractEventArgs {
    private Exception _error;
    private SensorConnectionMetadata _connectionMetadata;

    public ErrorEventArgs(SensorConnectionMetadata connectionMetadata, Exception error) {
        _error = error;
        _connectionMetadata = connectionMetadata;
    }

    public Exception getError() {
        return _error;
    }

    public SensorConnectionMetadata getSensorConnectionMetadata() {
        return _connectionMetadata;
    }
}
