package sensorserver.engine.events;

import sensorserver.engine.EngineStatus;

public class ShutdownEventArgs extends AbstractEventArgs {
    private EngineStatus _shutdownStatus;
    private Exception _error;

    public ShutdownEventArgs(EngineStatus status) {
        super();

        this._shutdownStatus = status;
        this._error = null;
    }

    public ShutdownEventArgs(EngineStatus status, Exception error) {
        super();

        this._shutdownStatus = status;
        this._error = error;
    }

    public EngineStatus getShutdownStatus() {
        return this._shutdownStatus;
    }

    public  Exception getError() {
        return this._error;
    }
}
