package sensorserver.engine.tasks;

import java.util.Date;

public class ScanningTaskResult {
    private Date _timeStarted;
    private long _duration;
    private String _ip;
    private String _hostname;
    private String _mac;
    private boolean _availability;

    public ScanningTaskResult(Date timeStarted, long duration, String ip, String hostname, boolean availability) {
        this._timeStarted = timeStarted;
        this._duration = duration;
        this._ip = ip;
        this._hostname = hostname;
        this._availability = availability;
    }

    public Date getTimeStarted() {
        return (Date) this._timeStarted.clone();
    }

    public long getDuration() {
        return this._duration;
    }

    public String getIP() {
        return this._ip;
    }

    public String getHostname() {
        return this._hostname;
    }

    public boolean isAvailable() {
        return this._availability;
    }
}
