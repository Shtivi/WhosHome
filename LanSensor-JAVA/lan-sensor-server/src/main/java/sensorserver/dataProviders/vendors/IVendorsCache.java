package sensorserver.dataProviders.vendors;

import java.io.IOException;

public interface IVendorsCache {
    String lookup(String mac);
    void saveEntry(String mac, String vendor);
}
