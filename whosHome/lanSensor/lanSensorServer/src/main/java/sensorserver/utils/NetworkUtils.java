package sensorserver.utils;

import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {
    public static List<String> getLanIpsList() {
        List<String> ipsList = new ArrayList<>();
        String ipFormat = "192.168.1.%d";

        for (int ipPosix = 1; ipPosix <= 255; ipPosix++) {
            String currIP = String.format(ipFormat, ipPosix);
            ipsList.add(currIP);
        }

        return ipsList;
    }

    public static boolean ping(String ip, long timeout) {
        return false;
    }
}
