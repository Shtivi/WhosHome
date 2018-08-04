package whosHome.common.sensors.client.messageDeserializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import whosHome.common.sensors.client.ISensorListener;
import whosHome.common.sensors.client.IdentificationData;

import java.util.ArrayList;

public class AllEntitiesMessageDeserializer<T extends IdentificationData> extends whosHome.common.sensors.client.messageDeserializers.AbstractMessageDeserializer<T> {
    public AllEntitiesMessageDeserializer(Class<T> entityType) {
        super(entityType);
    }

    @Override
    public void accept(JsonElement body, Iterable<ISensorListener<T>> listeners) {
        JsonArray rawEntitiesList = body.getAsJsonArray();
        Gson gson = new GsonBuilder().create();
        ArrayList<T> parsedEntities = new ArrayList<>();
        rawEntitiesList.forEach(entityAsJson -> {
            T currentEntity = gson.fromJson(entityAsJson, _entityType);
            parsedEntities.add(currentEntity);
        });
        listeners.forEach(listener -> listener.onEntitiesFetched(parsedEntities));
    }
}
