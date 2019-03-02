package sensorclient.events;

import java.util.Date;

public class AbstractSensorEventArgs {
    private Date _time;

    public AbstractSensorEventArgs() {
        this._time = new Date();
    }

    public AbstractSensorEventArgs(Date time) {
        if (time == null) {
            throw new IllegalArgumentException("event time cannot be null");
        }

        _time = time;
    }

    public Date getTime() {
        return (Date) this._time.clone();
    }
}
