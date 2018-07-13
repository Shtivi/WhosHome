package sensorserver.dataProviders.vendors;

import java.util.concurrent.Future;

public interface IVendorsProvider {
    Future<String> getVendorByMac(String mac);
}
