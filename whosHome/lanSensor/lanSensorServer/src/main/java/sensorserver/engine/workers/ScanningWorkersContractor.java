package sensorserver.engine.workers;

import sensorserver.engine.tasks.ScanningTask;
import sensorserver.engine.tasks.ScanningTaskResult;
import sensorserver.exceptions.InvalidOperationException;

import java.util.ArrayList;
import java.util.List;

public class ScanningWorkersContractor implements IContractor<ScanningTask> {
    private List<Runnable> _workers;
    private IScannerListener _listener;
    private IWorkersFactory<Runnable, ScanningTask> _workersFactory;
    private int _capacity;

    public ScanningWorkersContractor(IScannerListener listener, IWorkersFactory<Runnable, ScanningTask> workersFacotry, int capacity) {
        _listener = listener;
        _capacity = capacity;
        _workers = new ArrayList<>();
        _workersFactory = workersFacotry;
    }

    @Override
    public Runnable assign(ScanningTask task) throws InvalidOperationException {
        if (!this.hasAvailableWorkers()) {
            throw new InvalidOperationException("no workers available, maximal workers capacity is " + _capacity);
        } else {
            Runnable worker = _workersFactory.create(new WrapperScannerListener(), task);
            _workers.add(worker);

            return worker;
        }
    }

    @Override
    public boolean hasAvailableWorkers() {
        return _workers.size() < _capacity;
    }

    private void removeWorker(Runnable worker) {
        this._workers.remove(worker);
    }

    private class WrapperScannerListener implements IScannerListener {
        @Override
        public void onScanStarted(Runnable source, ScanningTask task) {
            _listener.onScanStarted(source, task);
        }

        @Override
        public void onScanCompleted(Runnable source, ScanningTask task, ScanningTaskResult result) {
            removeWorker(source);
            _listener.onScanCompleted(source, task, result);
        }

        @Override
        public void onScanFailed(Runnable source, ScanningTask task, Exception error) {
            removeWorker(source);
            _listener.onScanFailed(source, task, error);
        }
    }
}
