package sensorserver.utils.mocks;

import sensorserver.dataProviders.vendors.IVendorsCache;

public class VendorsCacheMock implements IVendorsCache {
    @Override
    public String lookup(String mac) {
        return null;
    }

    @Override
    public void saveEntry(String mac, String vendor) {

    }
}
