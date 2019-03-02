package whosHome.whosHomeApp.dataAccess.agents;

import whosHome.common.exceptions.WhosHomeException;

public class OperationFailedException extends WhosHomeException {
    public OperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
