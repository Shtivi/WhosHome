package whosHome.whosHomeApp.models;

import whosHome.common.Identifiable;

import javax.persistence.*;
import java.security.acl.Owner;

@Entity
@Table(name = "devices")
public class Device implements Identifiable<Integer> {
    @Id @GeneratedValue @Column(name = "device_id") private int deviceID;
    @Column(name = "mac_address") private String macAddress;
    @Column(name = "owner_id") private String ownerID;
    private Owner owner;

    public Device() {}

    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public Integer getID() {
        return getDeviceID();
    }
}
