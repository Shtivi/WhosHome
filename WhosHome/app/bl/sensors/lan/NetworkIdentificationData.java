package bl.sensors.lan;

import bl.identifiers.IdentificationData;

public class NetworkIdentificationData extends IdentificationData {
	// Data members
	private String ip;
	private String mac;
	
	// Ctor
	public NetworkIdentificationData(String ip, String mac) {
		this.setIp(ip);
		this.setMac(mac);
	}
	
	// Access methods
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}	
	
	// API
	
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		} else {
			// Cast to identification data
			NetworkIdentificationData other = (NetworkIdentificationData) obj;
			
			// Compare
			return (this.getMac().equals(other.getMac()));
		}
	}
	
	@Override
	public String toString() {
		return this.mac;
	}
}
