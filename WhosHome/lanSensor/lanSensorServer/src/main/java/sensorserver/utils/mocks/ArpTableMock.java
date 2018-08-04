package sensorserver.utils.mocks;

import sensorserver.engine.ArpTable;
import sensorserver.utils.NetworkUtils;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ArpTableMock extends ArpTable {
    private Random _random;

    /**
     * Creates a new ArpTableMock
     *
     * @throws IOException
     */
    public ArpTableMock() throws IOException {
        super("");
        _random = new Random();
        this.refresh();
    }

    @Override
    public void refresh() {
        super._entries.clear();
        List<String> ips = NetworkUtils.getLanIpsList();

        ips.forEach(ip -> {
            if (_random.nextBoolean()) {
                String randomMac = this.generateMacAddress();
                this.addEntry(ip, randomMac);
            }
        });
    }

    private String generateMacAddress() {
        byte[] macAddr = new byte[6];
        _random.nextBytes(macAddr);
        macAddr[0] = (byte)(macAddr[0] & (byte)254);

        StringBuilder stringBuilder = new StringBuilder(18);
        for (byte b : macAddr) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(":");
            }

            stringBuilder.append(String.format("%02x", b));
        }

        return stringBuilder.toString();
    }
}
