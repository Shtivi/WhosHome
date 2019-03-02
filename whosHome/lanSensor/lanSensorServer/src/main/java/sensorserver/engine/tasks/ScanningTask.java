package sensorserver.engine.tasks;

public class ScanningTask {
    private String _ip;
    private int _timeout;

    public ScanningTask(String ip, int timeout) {
        if (ip == null || ip.isEmpty()) {
            throw new IllegalArgumentException("ip cannot be null or empty");
        }

        if (timeout <= 0) {
            throw new IllegalArgumentException("timeout must be greater than 0");
        }

        this._ip = ip;
        this._timeout = timeout;
    }

    public String getIP() {
        return this._ip;
    }

    public int getTimeout() {
        return this._timeout;
    }
}
