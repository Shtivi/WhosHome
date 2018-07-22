package whosHome.common.sensors.messageDeserializers;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import whosHome.common.sensors.ISensorListener;
import whosHome.common.sensors.IdentificationData;
import whosHome.common.sensors.events.ActivityDetectionEventArgs;

import java.lang.reflect.ParameterizedType;
import java.util.function.BiConsumer;

public class DeviceConnectionParser<T extends IdentificationData> implements BiConsumer<JsonElement, Iterable<ISensorListener<T>>> {
    @Override
    public void accept(JsonElement eventBody, Iterable<ISensorListener<T>> listeners) {
        JsonObject body = eventBody.getAsJsonObject();
        ActivityDetectionEventArgs.Type type = ActivityDetectionEventArgs.Type.valueOf(body.get("_status").getAsString());
        JsonObject lanEntityJson = body.get("_entity").getAsJsonObject();
        Class<T> entityType = (Class<T>)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T lanEntity = new GsonBuilder().create().fromJson(lanEntityJson, entityType);
        ActivityDetectionEventArgs eventArgs = new ActivityDetectionEventArgs(type, lanEntity);
        listeners.forEach(listener -> listener.onActivityDetection(eventArgs));
    }
}
