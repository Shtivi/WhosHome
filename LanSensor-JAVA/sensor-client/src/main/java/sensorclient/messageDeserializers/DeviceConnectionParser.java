package sensorclient.messageDeserializers;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import sensorclient.ISensorListener;
import sensorclient.entities.LanEntity;
import sensorclient.events.DeviceConnectionEventArgs;

import java.util.function.BiConsumer;

import static com.sun.imageio.plugins.jpeg.JPEG.vendor;

public class DeviceConnectionParser implements BiConsumer<JsonElement, Iterable<ISensorListener>> {
    @Override
    public void accept(JsonElement eventBody, Iterable<ISensorListener> listeners) {
        JsonObject body = eventBody.getAsJsonObject();

        DeviceConnectionEventArgs.ActivityEventType type =
                DeviceConnectionEventArgs.ActivityEventType.valueOf(body.get("_status").getAsString());
        JsonObject lanEntityJson = body.get("_entity").getAsJsonObject();
        LanEntity lanEntity = new GsonBuilder().create().fromJson(lanEntityJson, LanEntity.class);
//        String ip = lanEntity.get("_ip").getAsString();
//        String mac = lanEntity.get("_mac").getAsString();
//        String hostname = lanEntity.get("_hostname").getAsString();
//        String vendor = lanEntity.get("_vendor").getAsString();

        DeviceConnectionEventArgs eventArgs = new DeviceConnectionEventArgs(type, lanEntity);

        listeners.forEach(listener -> listener.onDeviceEvent(eventArgs));
    }
}
