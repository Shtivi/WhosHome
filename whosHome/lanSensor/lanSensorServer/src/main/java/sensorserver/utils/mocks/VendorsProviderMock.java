package sensorserver.utils.mocks;

import sensorserver.dataProviders.vendors.IVendorsProvider;
import sensorserver.exceptions.MacAddressException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class VendorsProviderMock implements IVendorsProvider {
    @Override
    public Future<String> getVendorByMac(String mac) {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.completeExceptionally(new MacAddressException(mac, "not_found"));
        return future;
    }
}
