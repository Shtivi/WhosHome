package whosHome.common.sensors.client.messageDeserializers;

import com.google.gson.JsonElement;
import whosHome.common.sensors.client.ISensorListener;
import whosHome.common.sensors.client.IdentificationData;

import java.util.function.BiConsumer;

public abstract class AbstractMessageDeserializer<T extends IdentificationData> implements BiConsumer<JsonElement, Iterable<ISensorListener<T>>> {
    protected Class<T> _entityType;

    public AbstractMessageDeserializer(Class<T> entityType) {
        _entityType = entityType;
    }
}
