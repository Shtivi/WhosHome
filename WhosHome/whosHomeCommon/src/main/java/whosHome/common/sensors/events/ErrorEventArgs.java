package whosHome.common.sensors.events;

import whosHome.common.events.AbstractEventArgs;

public class ErrorEventArgs extends AbstractEventArgs {
    private Exception _error;

    public ErrorEventArgs(Exception error) {
        _error = error;
    }

    public Exception getError() {
        return _error;
    }
}
