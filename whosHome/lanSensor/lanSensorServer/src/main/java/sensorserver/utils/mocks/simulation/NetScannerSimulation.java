package sensorserver.utils.mocks.simulation;

import sensorserver.engine.tasks.ScanningTask;
import sensorserver.engine.tasks.ScanningTaskResult;
import sensorserver.engine.workers.IScannerListener;
import sensorserver.engine.workers.NetScanner;

import java.util.Date;
import java.util.Random;

public class NetScannerSimulation extends NetScanner {
    private static final int MAX_DELAY_MS = 7000;
    private static final int MIN_DELAY_MS = 2500;
    private static final float DEVICE_AVAILABILITY_PROBABILITY = 0.05f;
    private Random _random;

    public NetScannerSimulation(IScannerListener listener, ScanningTask task) {
        super(listener, task);
        _random = new Random();
    }

    private boolean generateAvailability() {
        int bound = (int)(DEVICE_AVAILABILITY_PROBABILITY * 100);
        int availability = _random.nextInt(bound);
        return availability == bound - 1;
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
                    generateAvailability());

            _listener.onScanCompleted(this, _task, scanResult);
        } catch (InterruptedException e) {
            _listener.onScanFailed(this, _task, e);
        }

    }
}
