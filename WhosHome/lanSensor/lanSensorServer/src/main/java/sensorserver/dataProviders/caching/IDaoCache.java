package sensorserver.dataProviders.caching;

import sensorserver.dataProviders.dao.IDataAccessor;
import sensorserver.dataProviders.dao.Identifiable;

public interface IDaoCache<I, V extends Identifiable<I>> extends ICache<I, V> {
    void setDao(IDataAccessor<I, V> dao);
    IDataAccessor<I, V> getDao();
}
