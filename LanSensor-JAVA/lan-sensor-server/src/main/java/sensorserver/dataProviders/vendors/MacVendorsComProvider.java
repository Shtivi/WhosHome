package sensorserver.dataProviders.vendors;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

public class MacVendorsComProvider implements IVendorsProvider {
    private String _apiUrl;
    private Logger _logger;
    private Map<String, String> _cache;
    private ScheduledExecutorService _executorService;
    private ConcurrentLinkedQueue<String> _requestsQueue;

    public MacVendorsComProvider(String apiUrl, ScheduledExecutorService executorService) {
        _logger = Logger.getLogger("MacVendorsComProviderLogger");
        _cache = new ConcurrentHashMap<>();
        _executorService = executorService;
        _apiUrl = apiUrl;
    }

    // TODO: make it async, and manage a queue to send only 1 request per second

    @Override
    public Future<String> getVendorByMac(String mac) {
//        if (_cache.containsKey(mac)) {
//            return _cache.get(mac);
//        } else {
//            _requestsQueue.add(mac);
//            try {
//                HttpResponse<String> response = Unirest.get(_apiUrl + "/{macAddr}")
//                        .routeParam("macAddr", mac)
//                        .asString();
//
//                if (response.getStatus() != 200) {
//                    _logger.error("macvendors.com returned an error: " + response.getStatusText());
//                    return null;
//                } else {
//                    String vendorQueryResult = response.getBody();
//                    _cache.put(mac, vendorQueryResult);
//                    return vendorQueryResult;
//                }
//            } catch (UnirestException e) {
//                _logger.error("error while fetching the mac from macvendors.com", e);
//                return null;
//            }
//        }
        return null;
    }


}
