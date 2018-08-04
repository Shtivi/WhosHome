package whosHome.common.sensors.client;

import com.google.gson.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import whosHome.common.exceptions.InvalidOperationException;
import whosHome.common.exceptions.WhosHomeException;
import whosHome.common.models.SensorConnectionMetadata;
import whosHome.common.sensors.client.commands.SensorCommand;
import whosHome.common.sensors.client.events.ErrorEventArgs;
import whosHome.common.sensors.client.events.StatusChangeEventArgs;
import whosHome.common.sensors.client.messageDeserializers.AllEntitiesMessageDeserializer;
import whosHome.common.sensors.client.messageDeserializers.ActivityDetectedMessageDeserializer;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class SensorConnection<T extends IdentificationData> implements ISensorConnection<T> {
    private Client _client;
    private Collection<ISensorListener<T>> _listeners;
    private Map<String, BiConsumer<JsonElement, Iterable<ISensorListener<T>>>> _serverMessageDeserializers;
    private SensorConnectionState _status;
    private SensorConnectionMetadata _connectionMetadata;
    private Class<T> _entityType;

    public SensorConnection(Class<T> entityType, SensorConnectionMetadata connectionMetadata) {
        String fullURI = connectionMetadata.getUrl() + ":" + connectionMetadata.getPort() + "/" + connectionMetadata.getPath();
        _client = new Client(URI.create(fullURI));
        _serverMessageDeserializers = new ConcurrentHashMap<>();
        _listeners = new ArrayList<>();
        _connectionMetadata = connectionMetadata;
        _entityType = entityType;

        this.initializeMessageSerializers();
        this.setStatus(SensorConnectionState.READY, "sensor waiting to be connected");
    }

    @Override
    public void connect() {
        if (_status == SensorConnectionState.READY) {
            _client.connect();
        } else {
            _client.reconnect();
        }
    }

    @Override
    public void disconnect() {
        if (_status == SensorConnectionState.CONNECTED) {
            _client.close();
        } else {
            throw new InvalidOperationException("status is '" + _status.name() + "', expected: '" + SensorConnectionState.CONNECTED.name() + "'");
        }
    }

    @Override
    public SensorConnectionState getStatus() {
        return _status;
    }

    @Override
    public void sendCommand(SensorCommand command) {
        _client.send(command.toString());
    }

    @Override
    public SensorConnectionMetadata getConnectionMetadata() {
        return _connectionMetadata;
    }

    @Override
    public boolean listen(ISensorListener<T> listener) {
        return _listeners.add(listener);
    }

    @Override
    public boolean removeListener(ISensorListener<T> listener) {
        return _listeners.remove(listener);
    }

    private void setStatus(SensorConnectionState newStatus, String reason) {
        StatusChangeEventArgs args = new StatusChangeEventArgs(_status, newStatus, reason);
        _status = newStatus;
        _listeners.forEach(listener -> listener.onStatusChange(args));
    }

    private void initializeMessageSerializers() {
        _serverMessageDeserializers.put("entityEvent", new ActivityDetectedMessageDeserializer<>(_entityType, this.getConnectionMetadata()));
        _serverMessageDeserializers.put("allEntities", new AllEntitiesMessageDeserializer<>(_entityType));
    }

    // Internal client class
    private class Client extends WebSocketClient {
        Gson gson = new GsonBuilder().create();

        public Client(URI serverUri) {
            super(serverUri);
        }

        @Override
        public void onOpen(ServerHandshake serverHandshake) {
            setStatus(SensorConnectionState.CONNECTED, "connection established");
        }

        @Override
        public void onMessage(String s) {
            JsonObject eventJson = new JsonParser().parse(s).getAsJsonObject();
            String eventName = eventJson.get("eventName").getAsString();

            if (!_serverMessageDeserializers.containsKey(eventName)) {
                throw new WhosHomeException(eventName);
            } else {
                JsonElement eventBody = eventJson.get("body");
                _serverMessageDeserializers.get(eventName).accept(eventBody, _listeners);
            }
        }

        @Override
        public void onClose(int i, String s, boolean b) {
            if (b) {
                setStatus(SensorConnectionState.READY, "the server has closed the connection");
            } else {
                setStatus(SensorConnectionState.READY, "connection closed according to user request");
            }
        }

        @Override
        public void onError(Exception e) {
            ErrorEventArgs args = new ErrorEventArgs(e);
            _listeners.forEach(listener -> listener.onError(args));
        }
    }
}
