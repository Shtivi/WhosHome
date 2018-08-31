package sensorserver.exceptions;

public class MacAddressException extends SensorException {
    private String _mac;

    public MacAddressException(String macAddress, String msg) {
        super(msg);
        _mac = macAddress;
    }

    public String getMacAddress() {
        return _mac;
    }
}
