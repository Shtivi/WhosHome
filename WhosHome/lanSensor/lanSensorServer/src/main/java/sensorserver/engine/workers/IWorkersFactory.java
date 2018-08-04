package sensorserver.engine.workers;

public interface IWorkersFactory<W extends Runnable, T> {
    W create(IScannerListener listener, T task);
}
