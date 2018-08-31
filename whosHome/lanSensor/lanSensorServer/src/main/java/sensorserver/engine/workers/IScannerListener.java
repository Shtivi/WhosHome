package sensorserver.engine.workers;

import sensorserver.engine.tasks.ScanningTask;
import sensorserver.engine.tasks.ScanningTaskResult;

public interface IScannerListener {
    void onScanStarted(Runnable source, ScanningTask task);
    void onScanCompleted(Runnable source, ScanningTask task, ScanningTaskResult result);
    void onScanFailed(Runnable source, ScanningTask task, Exception error);
}
