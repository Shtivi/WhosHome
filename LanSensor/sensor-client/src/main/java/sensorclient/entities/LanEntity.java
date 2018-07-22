package sensorclient.entities;

import whosHome.common.sensors.IdentificationData;

public class LanEntity extends IdentificationData {
    private String _ip;
    private String _mac;
    private String _hostname;
    private String _vendor;

    public LanEntity() {
    }

    @Override
    public String getIdentificationData() {
        return this.getMAC();
    }

    public String getIP() {
        return this._ip;
    }

    public String getMAC() {
        return this._mac;
    }

    public String getHostname() {
        return this._hostname;
    }

    public String getVendor() {
        return this._vendor;
    }

    public void setIP(String ip) {
        this._ip = ip;
    }

    public void setMAC(String mac) {
        this._mac = mac;
    }

    public void setHostname(String hostname) {
        this._hostname = hostname;
    }

    public void setVendor(String vendor) {
        this._vendor = vendor;
    }
}
