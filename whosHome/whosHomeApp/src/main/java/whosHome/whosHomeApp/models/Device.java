package whosHome.whosHomeApp.models;

import whosHome.common.Identifiable;

import javax.persistence.*;

@Entity
@Table(name = "devices")
public class Device implements Identifiable<String> {
    @Id @Column(name = "mac_address") private String macAddress;
    @Column(name = "owner_id") private String ownerID;
//    private Person owner;

    public Device() {}

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

//    public Person getOwner() {
//        return owner;
//    }
//
//    public void setOwner(Person owner) {
//        this.owner = owner;
//    }

    @Override
    public String getID() {
        return getMacAddress();
    }
}
