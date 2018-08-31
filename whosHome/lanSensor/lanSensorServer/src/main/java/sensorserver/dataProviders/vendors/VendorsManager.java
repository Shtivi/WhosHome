package sensorserver.dataProviders.vendors;

import com.google.inject.Inject;
import org.apache.log4j.Logger;
import sensorserver.dataProviders.caching.ICache;
import sensorserver.exceptions.MacAddressException;
import sensorserver.models.Vendor;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.sun.imageio.plugins.jpeg.JPEG.vendor;

public class VendorsManager {
    private Logger _logger;
    private IVendorsProvider _vendorsProvider;
    private ICache<String, Vendor> _cache;

    @Inject
    public VendorsManager(ICache<String, Vendor> cache, IVendorsProvider vendorsProvider) {
        _logger = Logger.getLogger("VendorsManager");
        _cache = cache;
        this.setVendorsProvider(vendorsProvider);
    }

    public Future<String> getVendorByMac(String mac) {
        CompletableFuture<String> promise = new CompletableFuture<>();

        String macPrefix = this.extractMacPrefix(mac);
        try {
            Optional<Vendor> vendorFromCache = _cache.retrieve(macPrefix);
            if (vendorFromCache.isPresent()) {
                Vendor vendor = vendorFromCache.get();
                promise.complete(vendor.getName());
            } else {
                _logger.info("fetching vendor of " + mac);
                Future<String> promisedVendor = _vendorsProvider.getVendorByMac(mac);
                String vendorName = promisedVendor.get();
                Vendor vendor = new Vendor(macPrefix, vendorName);
                _cache.cache(vendor);
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

    private synchronized void setVendorsProvider(IVendorsProvider vendorsProvider) {
        if (vendorsProvider == null) {
            throw new IllegalArgumentException("vendors provider cannot be null");
        }

        _vendorsProvider = vendorsProvider;
    }


    private String extractMacPrefix(String mac) {
        String macNoSeparators = mac.replaceAll("[-.]", "");
        String macPrefix = macNoSeparators.substring(0, 6);
        macPrefix = macPrefix.toLowerCase();
        return macPrefix;
    }
}
