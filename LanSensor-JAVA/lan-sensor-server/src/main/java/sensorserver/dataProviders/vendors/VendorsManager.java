package sensorserver.dataProviders.vendors;

import com.google.inject.Inject;
import org.apache.log4j.Logger;
import sensorserver.exceptions.MacAddressException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class VendorsManager {
    private Logger _logger;
    private IVendorsProvider _vendorsProvider;
    private IVendorsCache _cache;

    @Inject
    public VendorsManager(IVendorsCache cache, IVendorsProvider vendorsProvider) {
        _logger = Logger.getLogger("VendorsManager");
        _cache = cache;
        this.setVendorsProvider(vendorsProvider);
    }

    public Future<String> getVendorByMac(String mac) {
        CompletableFuture<String> promise = new CompletableFuture<>();

        try {
            String vendor = _cache.lookup(mac);
            if (vendor == null) {
                _logger.info("fetching vendor of " + mac);
                vendor = _vendorsProvider.getVendorByMac(mac).get();
                _cache.saveEntry(mac, vendor);
            }

            promise.complete(vendor);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof MacAddressException) {
                promise.complete(e.getCause().getMessage());
            } else {
                promise.completeExceptionally(e);
            }
        } catch (Exception e) {
            promise.completeExceptionally(e);
        }

        return promise;
    }

    public void close() {

    }

    private void setVendorsProvider(IVendorsProvider vendorsProvider) {
        if (vendorsProvider == null) {
            throw new IllegalArgumentException("vendors provider cannot be null");
        }

        _vendorsProvider = vendorsProvider;
    }
}
