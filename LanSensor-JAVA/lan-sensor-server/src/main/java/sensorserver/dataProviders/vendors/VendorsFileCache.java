package sensorserver.dataProviders.vendors;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import sensorserver.exceptions.SensorException;
import sensorserver.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VendorsFileCache implements IVendorsCache {
    private Map<String, String> _cache;
    private String _cachePath;
    private FileUtils _fileUtils;

    @Inject
    public VendorsFileCache(@Named("vendorsCachePath") String cacheFilePath, FileUtils fileUtils) throws FileNotFoundException {
        _cachePath = cacheFilePath;
        _fileUtils = fileUtils;
        this.readVendorsFile();
    }

    @Override
    public String lookup(String mac) {
        String macPrefix = this.extractMacPrefix(mac);
        String vendor = _cache.get(macPrefix);
        return vendor;
    }

    @Override
    public void saveEntry(String mac, String vendor) {
        String macPrefix = this.extractMacPrefix(mac);
        _cache.put(macPrefix, vendor);
        try {
            saveToFile();
        } catch (IOException e) {
            throw new SensorException(e.getMessage(), e);
        }
    }

    private String extractMacPrefix(String mac) {
        String macNoSeparators = mac.replaceAll("[-.]", "");
        String macPrefix = macNoSeparators.substring(0, 6);
        macPrefix = macPrefix.toLowerCase();
        return macPrefix;
    }

    private void saveToFile() throws IOException {
        String cacheEntriesJson = new Gson().toJson(_cache);
        _fileUtils.writeToFile(_cachePath, cacheEntriesJson);
    }

    private void readVendorsFile() throws FileNotFoundException {
        String cacheFileContents = _fileUtils.readFile(_cachePath);
        _cache = new Gson().fromJson(cacheFileContents, ConcurrentHashMap.class);

        if (_cache == null) {
            _cache = new ConcurrentHashMap();
        }
    }
}
