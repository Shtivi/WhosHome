package sensorserver.exceptions;

public class SensorException extends RuntimeException {
    public SensorException(String msg) {
        super(msg);
    }

    public SensorException(String msg, Exception error) {
        super(msg, error);
    }
}
