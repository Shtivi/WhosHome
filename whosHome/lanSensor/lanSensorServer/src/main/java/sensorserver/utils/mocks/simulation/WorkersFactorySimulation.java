package sensorserver.utils.mocks.simulation;

import sensorserver.engine.tasks.ScanningTask;
import sensorserver.engine.workers.IScannerListener;
import sensorserver.engine.workers.IWorkersFactory;

public class WorkersFactorySimulation implements IWorkersFactory<Runnable, ScanningTask> {
    @Override
    public Runnable create(IScannerListener listener, ScanningTask task) {
        return new NetScannerSimulation(listener, task);
    }
}
