package whosHome.whosHomeApp.engine.events;

import whosHome.common.events.AbstractEventArgs;
import whosHome.whosHomeApp.engine.WhosHomeEngine;

import java.util.Optional;

public class EngineStatusChangedEventArgs extends AbstractEventArgs {
    private WhosHomeEngine.Status oldStatus;
    private WhosHomeEngine.Status newStatus;
    private String reason;
    private Throwable error;

    private EngineStatusChangedEventArgs() {
        super();
    }

    public WhosHomeEngine.Status getOldStatus() {
        return oldStatus;
    }

    public WhosHomeEngine.Status getNewStatus() {
        return newStatus;
    }

    public String getReason() {
        return reason;
    }

    public Optional<Throwable> getError() {
        return Optional.ofNullable(error);
    }

    public static class Builder {
        EngineStatusChangedEventArgs args;

        public Builder(WhosHomeEngine.Status oldStatus, WhosHomeEngine.Status newStatus) {
            args.oldStatus = oldStatus;
            args.newStatus = newStatus;
            args.reason = "";
        }

        public Builder withReason(String reason) {
            args.reason = reason;
            return this;
        }

        public Builder withError(Throwable error) {
            args.error = error;
            return this;
        }

        public EngineStatusChangedEventArgs build() {
            return args;
        }
    }
}
