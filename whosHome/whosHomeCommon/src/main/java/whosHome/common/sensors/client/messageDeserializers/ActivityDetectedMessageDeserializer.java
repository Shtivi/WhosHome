package whosHome.common.sensors.client.messageDeserializers;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import whosHome.common.models.SensorConnectionMetadata;
import whosHome.common.sensors.client.ISensorListener;
import whosHome.common.sensors.client.IdentificationData;
import whosHome.common.sensors.client.events.ActivityDetectionEventArgs;

public class ActivityDetectedMessageDeserializer<T extends IdentificationData> extends AbstractMessageDeserializer<T> {
    private SensorConnectionMetadata _sensorConnectionMetadata;

    public ActivityDetectedMessageDeserializer(Class<T> entityType, SensorConnectionMetadata connectionMetadata) {
        super(entityType);
        _sensorConnectionMetadata = connectionMetadata;
    }

    @Override
    public void accept(JsonElement eventBody, Iterable<ISensorListener<T>> listeners) {
        JsonObject body = eventBody.getAsJsonObject();
        ActivityDetectionEventArgs.Type type = ActivityDetectionEventArgs.Type.valueOf(body.get("_status").getAsString());
        JsonObject lanEntityJson = body.get("_entity").getAsJsonObject();
        T lanEntity = new GsonBuilder().create().fromJson(lanEntityJson, _entityType);
        ActivityDetectionEventArgs eventArgs = new ActivityDetectionEventArgs(type, lanEntity, _sensorConnectionMetadata);
        listeners.forEach(listener -> listener.onActivityDetection(eventArgs));
    }
}
