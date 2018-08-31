package sensorserver.engine.entities;

import sensorserver.engine.events.EntityEventArgs;
import sensorserver.events.Event;

import java.util.Collection;

public interface IEntitiesHolder<T> {
    void add(T entity);
    void remove(T entity);
    LanEntity getEntityById(Object id);
    Collection<T> getPresentEntities();
    Event<EntityEventArgs> entityIn();
    Event<EntityEventArgs> entityOut();
}
