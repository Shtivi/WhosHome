package sensorserver.engine.entities;

import sensorserver.engine.events.EntityEventArgs;
import sensorserver.events.Event;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LanEntitiesHolder implements IEntitiesHolder<LanEntity> {
    private Map<String, LanEntity> _holder;
    private Event<EntityEventArgs> _entityInEvent;
    private Event<EntityEventArgs> _entityOutEvent;

    public LanEntitiesHolder() {
        _holder = new ConcurrentHashMap<>();
        _entityInEvent = new Event<>();
        _entityOutEvent = new Event<>();
    }

    @Override
    public void add(LanEntity entity) {
        if (!_holder.containsKey(entity.getIP())) {
            _holder.put(entity.getIP(), entity);
            _entityInEvent.dispatch(new EntityEventArgs(entity, EntityStatus.IN));
        }
    }

    @Override
    public void remove(LanEntity entity) {
        if (_holder.containsKey(entity.getIP())) {
            _holder.remove(entity.getIP());
            _entityInEvent.dispatch(new EntityEventArgs(entity, EntityStatus.OUT));
        }
    }

    @Override
    public LanEntity getEntityById(Object id) {
        if (id instanceof String) {
            return _holder.get(id);
        } else {
            throw new IllegalArgumentException("id not a string");
        }
    }

    @Override
    public Collection<LanEntity> getPresentEntities() {
        return _holder.values();
    }

    @Override
    public Event<EntityEventArgs> entityIn() {
        return this._entityInEvent;
    }

    @Override
    public Event<EntityEventArgs> entityOut() {
        return this._entityOutEvent;
    }
}
