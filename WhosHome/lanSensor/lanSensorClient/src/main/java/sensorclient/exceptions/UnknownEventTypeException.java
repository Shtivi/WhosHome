package sensorclient.exceptions;

public class UnknownEventTypeException extends InvalidOperationException {
    public UnknownEventTypeException(String eventType) {
        super("unknown event type: " + eventType);
    }
}
