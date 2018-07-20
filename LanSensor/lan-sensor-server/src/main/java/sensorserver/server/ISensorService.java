package sensorserver.server;

import sensorserver.events.Event;

import java.net.InetSocketAddress;

public interface ISensorService {
    void broadcastEvent(String eventTitle, Object eventBody);
    void broadcastEvent(InetSocketAddress address, String eventTitle, Object eventBody);
    void start();
    void stop() throws Exception;
    Event<MessageReceivedEventArgs> onMessageReceived();
    Event<ClientConnectionEventArgs> onClientConnection();
}
