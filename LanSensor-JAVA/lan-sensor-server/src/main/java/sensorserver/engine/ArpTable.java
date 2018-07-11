package sensorserver.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArpTable {
    private static String LAN_IP_PATTERN = "(192.168.1.([0-9]{1,3}))";
    private static String MAC_PATTERN = "(([0-9a-f]{2}[-]){5}([0-9a-f]{2}))";
    private static String ARP_ENTRY_PATTERN = String.format("(%s([.]+)%s)", LAN_IP_PATTERN, MAC_PATTERN);

    protected Map<String, String> _entries;
    protected Map<String, List<Consumer<String>>> _ipListeners;
    protected String _arpCmd;

    /**
     * Creates a new ArpTable instance and initializes it with the current entries in the arp cache.
     * @param arpCmd - name of command to run for fetching the arp entries (arp -a, arp...)
     * @throws IOException
     */
    public ArpTable(String arpCmd) throws IOException {
        _entries = new ConcurrentHashMap<>();
        _ipListeners = new ConcurrentHashMap<>();

        if (arpCmd == null) {
            throw new IllegalArgumentException("arp command name cannot be null");
        }
        _arpCmd = arpCmd;
    }

    /**
     * Clears the current known entries, runs "arp -a" command and parses te output.
     * @throws IOException when fails to execute "arp -a"
     */
    public void refresh() throws IOException {
        String rawArpOutput = this.readArpTableData();
        this._entries.clear();
        this.parseRawArpData(rawArpOutput);
    }

    /**
     * Fetches the mac address by its ip, if already detected.
     * If the current ip was not found yet, returns null.
     * If you want to be notified once an ip address appears in the arp table, use the 'onceDetected' method.
     * @param IP the ip address of the device
     * @return The mac address of the device, if detected in the arp table before. Otherwise returns null.
     */
    public String getMAC(String IP) {
        return _entries.get(IP);
    }

    /**
     * Gets an ip address to listen to and a callback.
     * Once this ip was found in the arp entries, calls the callback with the linked mac address.
     * @param IP - ip to listen to
     * @param callback - A consumer that takes a String argument (the mac address). Will be called when the ip address is detected.
     */
    public void onceDetected(String IP, Consumer<String> callback) {
        if (this.getMAC(IP) != null) {
            callback.accept(this.getMAC(IP));
        } else {
            if (!_ipListeners.containsKey(IP)) {
                _ipListeners.put(IP, new ArrayList<>());
            }

            _ipListeners.get(IP).add(callback);
        }
    }

    protected void addEntry(String ip, String mac) {
        _entries.put(ip, mac);
        this.resolveDetection(ip, mac);
    }

    private void resolveDetection(String ip, String mac) {
        if (_ipListeners.containsKey(ip)) {
            List<Consumer<String>> consumers = _ipListeners.get(ip);
            consumers.forEach(c -> c.accept(mac));
            consumers.clear();
        }
    }

    private String readArpTableData() throws IOException {
        Scanner s = new Scanner(Runtime.getRuntime().exec(_arpCmd).getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private void parseRawArpData(String cmdOutput) {
        Pattern ipPattern = Pattern.compile(LAN_IP_PATTERN);
        Pattern macAddrPattern = Pattern.compile(MAC_PATTERN);
        Pattern filteredArpEntryPattern = Pattern.compile(ARP_ENTRY_PATTERN);
        Matcher entriesMatcher = filteredArpEntryPattern.matcher(cmdOutput);

        while (entriesMatcher.find()) {
            String currEntry = entriesMatcher.group(0);

            Matcher ipMatcher = ipPattern.matcher(currEntry);
            ipMatcher.find();
            String entryIP = ipMatcher.group(0);

            Matcher macMatcher = macAddrPattern.matcher(currEntry);
            macMatcher.find();
            String entryMAC = macMatcher.group(0);

            this.addEntry(entryIP, entryMAC);
        }
    }
}