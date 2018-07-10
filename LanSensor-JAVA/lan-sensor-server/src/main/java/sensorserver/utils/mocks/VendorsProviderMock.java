package sensorserver.utils.mocks;

import sensorserver.dataProviders.IVendorsProvider;

public class VendorsProviderMock implements IVendorsProvider {
    @Override
    public String getVendorByMac(String mac) {
        return null;
    }
}
