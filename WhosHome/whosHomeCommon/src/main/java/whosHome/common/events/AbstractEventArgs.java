package whosHome.common.events;

import java.util.Date;

public class AbstractEventArgs {
    private Date _time;

    public AbstractEventArgs() {
        this._time = new Date();
    }

    public AbstractEventArgs(Date time) {
        if (time == null) {
            throw new IllegalArgumentException("event time cannot be null");
        }

        _time = time;
    }

    public Date getTime() {
        return (Date) this._time.clone();
    }
}
