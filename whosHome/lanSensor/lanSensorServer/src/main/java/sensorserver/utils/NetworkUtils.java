package sensorserver.utils;

import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {
    public static List<String> getLanIpsList() {
        return getLanIpsList(255);
    }

    public static List<String> getLanIpsList(int bound) {
        if (bound > 255 || bound < 0)
            throw new IllegalArgumentException("Bound must be between 0 and 255");

        List<String> ipsList = new ArrayList<>();
        String ipFormat = "192.168.1.%d";

        for (int ipPosix = 1; ipPosix <= bound; ipPosix++) {
            String currIP = String.format(ipFormat, ipPosix);
            ipsList.add(currIP);
        }

        return ipsList;
    }

    public static boolean ping(String ip, long timeout) {
        return false;
    }
}
