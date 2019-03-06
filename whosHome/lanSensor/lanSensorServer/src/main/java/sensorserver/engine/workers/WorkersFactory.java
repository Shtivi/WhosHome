package sensorserver.engine.workers;

import sensorserver.engine.tasks.ScanningTask;

public class WorkersFactory implements IWorkersFactory<Runnable, ScanningTask> {
    @Override
    public Runnable create(IScannerListener listener, ScanningTask task) {
        return new NetScanner(listener, task);
    }
}
