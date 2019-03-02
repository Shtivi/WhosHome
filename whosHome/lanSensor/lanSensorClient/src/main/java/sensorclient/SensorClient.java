package sensorclient;

import com.google.gson.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import sensorclient.commands.SensorCommand;
import sensorclient.events.ErrorEventArgs;
import sensorclient.events.StatusChangeEventArgs;
import sensorclient.exceptions.InvalidOperationException;
import sensorclient.exceptions.UnknownEventTypeException;
import sensorclient.messageDeserializers.AllEntitiesParser;
import sensorclient.messageDeserializers.DeviceConnectionParser;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class SensorClient implements ISensorClient {
    private Client _client;
    private Collection<ISensorListener> _listeners;
    private Map<String, BiConsumer<JsonElement, Iterable<ISensorListener>>> _serverMessageDeserializers;
    private SensorClientStatus _status;

    /** Creates a new SensorClient object.
     *
     * @param uri - sensor uri without port, example: http://192.168.1.32
     * @param port - the port, in the given uri, on witch the sensor service runs.
     */
    public SensorClient(String uri, int port) {
        this(uri + ":" + port);
    }

    /** Creates a new SensorClient object.
     *
     * @param fullURI - the full uri to the sensor service, including uri and port. Example: http://192.168.1.32:6000
     */
    public SensorClient(String fullURI) {
        _client = new Client(URI.create(fullURI));
        _serverMessageDeserializers = new ConcurrentHashMap<>();
        _listeners = new ArrayList<>();

        this.initializeMessageSerializers();
        this.setStatus(SensorClientStatus.READY, "sensor waiting to be connected");
    }

    @Override
    public void connect() {
        if (_status == SensorClientStatus.READY) {
            _client.connect();
        } else {
            _client.reconnect();
        }
    }

    @Override
    public void disconnect() {
        if (_status == SensorClientStatus.CONNECTED) {
            _client.close();
        } else {
            throw new InvalidOperationException("status is '" + _status.name() + "', expected: '" + SensorClientStatus.CONNECTED.name() + "'");
        }
    }

    @Override
    public SensorClientStatus getStatus() {
        return _status;
    }

    @Override
    public void sendCommand(SensorCommand command) {
        _client.send(command.toString());
    }

    @Override
    public boolean listen(ISensorListener listener) {
        return _listeners.add(listener);
    }

    @Override
    public boolean removeListener(ISensorListener listener) {
        return _listeners.remove(listener);
    }

    private void setStatus(SensorClientStatus newStatus, String reason) {
        StatusChangeEventArgs args = new StatusChangeEventArgs(_status, newStatus, reason);
        _status = newStatus;
        _listeners.forEach(listener -> listener.onStatusChange(args));
    }

    private void initializeMessageSerializers() {
        _serverMessageDeserializers.put("entityEvent", new DeviceConnectionParser());
        _serverMessageDeserializers.put("allEntities", new AllEntitiesParser());
    }

    // Internal client class
    private class Client extends WebSocketClient {
        Gson gson = new GsonBuilder().create();

        public Client(URI serverUri) {
            super(serverUri);
        }

        @Override
        public void onOpen(ServerHandshake serverHandshake) {
            setStatus(SensorClientStatus.CONNECTED, "connection established");
        }

        @Override
        public void onMessage(String s) {
            JsonObject eventJson = new JsonParser().parse(s).getAsJsonObject();
            String eventName = eventJson.get("eventName").getAsString();

            if (!_serverMessageDeserializers.containsKey(eventName)) {
                throw new UnknownEventTypeException(eventName);
            } else {
                JsonElement eventBody = eventJson.get("body");
                _serverMessageDeserializers.get(eventName).accept(eventBody, _listeners);
            }
        }

        @Override
        public void onClose(int i, String s, boolean b) {
            if (b) {
                setStatus(SensorClientStatus.DISCONNECTED, "the server has closed the connection");
            } else {
                setStatus(SensorClientStatus.DISCONNECTED, "connection closed according to user request");
            }
        }

        @Override
        public void onError(Exception e) {
            ErrorEventArgs args = new ErrorEventArgs(e);
            _listeners.forEach(listener -> listener.onError(args));
        }
    }
}
