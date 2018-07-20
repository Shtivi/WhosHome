package sensorclient.messageDeserializers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import sensorclient.ISensorListener;
import sensorclient.entities.LanEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class AllEntitiesParser implements BiConsumer<JsonElement, Iterable<ISensorListener>> {
    @Override
    public void accept(JsonElement body, Iterable<ISensorListener> listeners) {
        JsonArray rawEntitiesList = body.getAsJsonArray();
        Type listType = new TypeToken<ArrayList<LanEntity>>(){}.getType();
        Iterable<LanEntity> parsedEntities = new Gson().fromJson(rawEntitiesList, listType);
        listeners.forEach(listener -> listener.allEntitiesFetched(parsedEntities));
    }
}
