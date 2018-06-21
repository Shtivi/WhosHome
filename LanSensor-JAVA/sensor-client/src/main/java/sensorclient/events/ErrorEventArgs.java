package sensorclient.events;

public class ErrorEventArgs extends AbstractSensorEventArgs {
    private Exception _error;

    public ErrorEventArgs(Exception error) {
        _error = error;
    }

    public Exception getError() {
        return _error;
    }
}
