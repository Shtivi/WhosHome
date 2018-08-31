package sensorserver.engine.tasks;

import sensorserver.exceptions.InvalidOperationException;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class NetScanTasksSupplier implements ITasksSupplier<ScanningTask> {
    private Queue<ScanningTask> _tasksQueue;
    private int _taskTimeout;

    public NetScanTasksSupplier(List<String> ipsToScan, int timeoutMiliseconds) {
        this._tasksQueue = new LinkedBlockingQueue<ScanningTask>();
        _taskTimeout = timeoutMiliseconds;

        this.initializeQueue(ipsToScan);
    }

    @Override
    public boolean hasAvailableTasks() throws InvalidOperationException {
        return !_tasksQueue.isEmpty();
    }

    @Override
    public ScanningTask pullTask() {
        if (!this.hasAvailableTasks()) {
            throw new InvalidOperationException("tasks queue is empty");
        } else {
            return _tasksQueue.poll();
        }
    }

    @Override
    public void pushTask(ScanningTask task) {
        _tasksQueue.add(task);
    }

    private void initializeQueue(List<String> ipsList) {
        for (String ipAddr : ipsList) {
            ScanningTask task = new ScanningTask(ipAddr, _taskTimeout);
            this._tasksQueue.add(task);
        }
    }
}
