package sensorserver.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
}
