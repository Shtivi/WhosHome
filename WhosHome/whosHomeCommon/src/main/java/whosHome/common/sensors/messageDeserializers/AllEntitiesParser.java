package whosHome.common.sensors.messageDeserializers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import whosHome.common.sensors.ISensorListener;
import whosHome.common.sensors.IdentificationData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class AllEntitiesParser<T extends IdentificationData> implements BiConsumer<JsonElement, Iterable<ISensorListener<T>>> {
    @Override
    public void accept(JsonElement body, Iterable<ISensorListener<T>> listeners) {
        JsonArray rawEntitiesList = body.getAsJsonArray();
        Type listType = new TypeToken<ArrayList<T>>(){}.getType();
        Iterable<T> parsedEntities = new Gson().fromJson(rawEntitiesList, listType);
        listeners.forEach(listener -> listener.onEntitiesFetched(parsedEntities));
    }
}
