package sensorserver.engine.workers;

import sensorserver.engine.tasks.ScanningTask;
import sensorserver.engine.tasks.ScanningTaskResult;

public interface IScannerListener {
    void onScanStarted(NetScanner source, ScanningTask task);
    void onScanCompleted(NetScanner source, ScanningTask task, ScanningTaskResult result);
    void onScanFailed(NetScanner source, ScanningTask task, Exception error);
}
