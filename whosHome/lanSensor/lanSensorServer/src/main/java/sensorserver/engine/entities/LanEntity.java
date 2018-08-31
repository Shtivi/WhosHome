package sensorserver.engine.entities;

public class LanEntity {
    private String _ip;
    private String _mac;
    private String _hostname;
    private String _vendor;

    private LanEntity() {
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

    private void setIP(String ip) {
        if (ip == null || ip.isEmpty()) {
            throw new IllegalArgumentException("ip address cannot be null or an empty string");
        }

        this._ip = ip;
    }

    private void setMAC(String mac) {
        this._mac = mac;
    }

    private void setHostname(String hostname) {
        if (hostname == null || hostname.isEmpty()) {
            throw new IllegalArgumentException("host name cannot be null or an empty string");
        }

        this._hostname = hostname;
    }

    private void setVendor(String vendor) {
        this._vendor = vendor;
    }

    public static class LanEntityBuilder {
        private LanEntity _entity = new LanEntity();

        public LanEntityBuilder(String ip, String hostname) {
            _entity.setIP(ip);
            _entity.setHostname(hostname);
        }

        public LanEntityBuilder setMAC(String mac) {
            _entity.setMAC(mac);
            return this;
        }

        public LanEntityBuilder setVendor(String vendor) {
            _entity.setVendor(vendor);
            return this;
        }

        public LanEntity build() {
            return _entity;
        }
    }
}
