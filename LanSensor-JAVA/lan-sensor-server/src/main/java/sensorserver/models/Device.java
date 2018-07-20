package sensorserver.models;

import sensorserver.dataProviders.dao.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Device")
public class Device implements Identifiable<String> {
    @Id
    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "mac_address_prefix")
    private String macAddressPrefix;

    @Column(name = "insertion_time")
    private Date insertionTime;

    @Override
    public String getId() {
        return this.macAddress;
    }
}
