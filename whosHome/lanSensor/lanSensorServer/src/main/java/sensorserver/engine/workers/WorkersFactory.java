package sensorserver.engine.workers;

import sensorserver.engine.tasks.ScanningTask;

public class WorkersFactory implements IWorkersFactory<Runnable, ScanningTask> {
    @Override
    public NetScannerV2 create(IScannerListener listener, ScanningTask task) {
        return new NetScannerV2(listener, task);
    }
}
