package sensorserver.utils.mocks;

import sensorserver.engine.tasks.ScanningTask;
import sensorserver.engine.tasks.ScanningTaskResult;
import sensorserver.engine.workers.IScannerListener;
import sensorserver.engine.workers.NetScanner;

import java.util.Date;
import java.util.Random;

public class NetScannerMock extends NetScanner {
    private static final int MAX_DELAY_MS = 2000;
    private static final int MIN_DELAY_MS = 500;
    private Random _random;

    public NetScannerMock(IScannerListener listener, ScanningTask task) {
        super(listener, task);
        _random = new Random();
    }

    @Override
    public void run() {
        _listener.onScanStarted(this, _task);

        int duration = _random.nextInt(MAX_DELAY_MS - MIN_DELAY_MS) + MIN_DELAY_MS;
        try {
            Thread.sleep(duration);
            ScanningTaskResult scanResult = new ScanningTaskResult(
                    new Date(),
                    duration,
                    _task.getIP(),
                    _task.getIP(),
                    _random.nextBoolean());

            _listener.onScanCompleted(this, _task, scanResult);
        } catch (InterruptedException e) {
            _listener.onScanFailed(this, _task, e);
        }

    }
}
