package sensorserver.engine.events;

import sensorserver.engine.entities.EntityStatus;
import sensorserver.engine.entities.LanEntity;

public class EntityEventArgs extends AbstractEventArgs {
    private EntityStatus _status;
    private LanEntity _entity;

    public EntityEventArgs(LanEntity entity, EntityStatus status) {
        _entity = entity;
        _status = status;
    }

    public EntityStatus getStatus() {
        return this._status;
    }

    public LanEntity getEntity() {
        return this._entity;
    }
}
