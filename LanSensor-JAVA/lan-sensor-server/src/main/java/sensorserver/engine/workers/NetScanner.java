package sensorserver.engine.workers;

import org.apache.commons.lang.time.StopWatch;
import sensorserver.engine.tasks.ScanningTask;
import sensorserver.engine.tasks.ScanningTaskResult;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

public class NetScanner implements Runnable {
    private IScannerListener _listener;
    private ScanningTask _task;

    public NetScanner(IScannerListener listener, ScanningTask task) {
        if (listener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }

        if (task == null) {
            throw new IllegalArgumentException("task parameter cannot be null");
        }

        _task = task;
        _listener = listener;
    }

    @Override
    public void run() {
        _listener.onScanStarted(this, _task);
        try {
            InetAddress ipAddr = InetAddress.getByName(this._task.getIP());

            StopWatch stopper = new StopWatch();
            stopper.start();

            String hostname = ipAddr.getHostName();
            boolean available = ipAddr.isReachable(_task.getTimeout());

            stopper.stop();
            ScanningTaskResult scanResult = new ScanningTaskResult(
                    new Date(stopper.getStartTime()),
                    stopper.getTime(),
                    this._task.getIP(),
                    hostname,
                    available
            );

            _listener.onScanCompleted(this, _task, scanResult);
        } catch (Exception e) {
            _listener.onScanFailed(this, _task, e);
        }
    }
}
