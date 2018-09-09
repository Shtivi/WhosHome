package sensorserver.utils.mocks.simulation;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import sensorserver.engine.ArpTable;
import sensorserver.utils.NetworkUtils;

import java.io.IOException;
import java.util.*;

/**
 * Simulates the behaviour of the Arp Table object.
 * Will detect only mac addresses appear in the configuration under 'simulation'.
*/
public class ArpTableSimulation extends ArpTable {
    private static Map<String, String> generateIpToMacMapping(List<String> ipAddresses, List<String> macAddresses) {
        HashMap<String, String> mapping = new HashMap<>();

        int runUntil = ipAddresses.size() < macAddresses.size() ? ipAddresses.size() : macAddresses.size();
        for (int index = 0; index < runUntil; index++) {
            mapping.put(ipAddresses.get(index), macAddresses.get(index));
        }

        return mapping;
    }

    private Random _random;
    private Iterable<String> _macAddresses;
    private Map<String, String> _ipToMacMapping;

    @Inject
    public ArpTableSimulation(@Named("macAddressesSimulation") List<String> macAddresses) throws IOException {
        super("");

        if (macAddresses == null) {
            throw new IllegalArgumentException("Mac addresses list cannot be null");
        }

        if (!macAddresses.iterator().hasNext()) {
            throw new IllegalArgumentException("Mac addresses list has no items");
        }

        _random = new Random();
        _macAddresses = macAddresses;
        _ipToMacMapping = generateIpToMacMapping(NetworkUtils.getLanIpsList(), macAddresses);
    }

    @Override
    public void refresh() {
        super._entries.clear();
        List<String> ips = NetworkUtils.getLanIpsList();

        ips.forEach(ip -> {
            if (_random.nextBoolean()) {
                String randomMac = _ipToMacMapping.get(ip);
                this.addEntry(ip, randomMac);
            }
        });
    }
}