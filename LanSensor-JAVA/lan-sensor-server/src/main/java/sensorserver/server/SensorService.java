package sensorserver.server;

import com.google.gson.*;
import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import sensorserver.events.Event;

import java.net.InetSocketAddress;

public class SensorService extends WebSocketServer implements ISensorService {
    private static Logger _logger = Logger.getLogger(SensorService.class);

    private Event<MessageReceivedEventArgs> _messageReceivedEvent;
    private Event<ClientConnectionEventArgs> _clientConnectedEvent;

    public SensorService(int port) {
        super(new InetSocketAddress(port));
        this._messageReceivedEvent = new Event<>();
        this._clientConnectedEvent = new Event<>();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        _logger.info("Client connected: " + webSocket.getRemoteSocketAddress());

        ClientConnectionEventArgs args = new ClientConnectionEventArgs(webSocket.getRemoteSocketAddress());
        _clientConnectedEvent.dispatch(args);
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        _logger.info("Client disconnected: " + webSocket.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        MessageReceivedEventArgs args = new MessageReceivedEventArgs(webSocket.getRemoteSocketAddress(), s);
        _messageReceivedEvent.dispatch(args);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        _logger.error(String.format("Client exception '%s'", webSocket.getRemoteSocketAddress()), e);
    }

    @Override
    public void onStart() {
        _logger.info(String.format("service started on port '%d'", this.getPort()));
    }

    @Override
    public void broadcastEvent(String eventName, Object event) {
        String json = this.buildEventJson(eventName, event);
        broadcast(json);
    }

    @Override
    public void broadcastEvent(InetSocketAddress address, String eventTitle, Object eventBody) {
        WebSocket addressee = this.findClient(address);

        if (addressee == null) {
            throw new ClientNotFoundException(address);
        } else {
            String eventJson = this.buildEventJson(eventTitle, eventBody);
            addressee.send(eventJson);
        }
    }

    @Override
    public Event<MessageReceivedEventArgs> onMessageReceived() {
        return this._messageReceivedEvent;
    }

    @Override
    public Event<ClientConnectionEventArgs> onClientConnection() {
        return _clientConnectedEvent;
    }

    private WebSocket findClient(InetSocketAddress address) {
        WebSocket webSocket = this.getConnections()
                .stream()
                .filter(client -> client.getRemoteSocketAddress().equals(address))
                .findFirst()
                .orElseGet(() -> null);

        return webSocket;
    }

    private String buildEventJson(String eventName, Object eventBody) {
        Gson gson = new GsonBuilder().create();
        JsonObject eventJson = new JsonObject();
        eventJson.add("eventName", new JsonPrimitive(eventName));
        eventJson.add("body", gson.toJsonTree(eventBody));
        String result = gson.toJson(eventJson);

        return result;
    }
}
