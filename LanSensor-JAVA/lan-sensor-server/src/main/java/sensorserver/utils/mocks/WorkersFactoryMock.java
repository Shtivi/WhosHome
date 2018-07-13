package sensorserver.utils.mocks;

import sensorserver.engine.tasks.ScanningTask;
import sensorserver.engine.workers.IScannerListener;
import sensorserver.engine.workers.IWorkersFactory;

public class WorkersFactoryMock implements IWorkersFactory<Runnable, ScanningTask> {
    @Override
    public Runnable create(IScannerListener listener, ScanningTask task) {
        return new NetScannerMock(listener, task);
    }
}
