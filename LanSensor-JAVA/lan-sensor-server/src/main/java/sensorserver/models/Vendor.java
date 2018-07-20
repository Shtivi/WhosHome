package sensorserver.models;

import sensorserver.dataProviders.dao.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vendors")
public class Vendor implements Identifiable<String> {
    @Id
    @Column(name = "mac_address_prefix", columnDefinition="VARCHAR(6)")
    private String _macAddressPrefix;

    @Column(name = "name")
    private String _name;

    public Vendor() {}

    public Vendor(String _macAddressPrefix, String _name) {
        setMacAddressPrefix(_macAddressPrefix);
        setName(_name);
    }

    public String getMacAddressPrefix() {
        return _macAddressPrefix;
    }

    public void setMacAddressPrefix(String _macAddressPrefix) {
        this._macAddressPrefix = _macAddressPrefix;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    @Override
    public String getId() {
        return this.getMacAddressPrefix();
    }
}
