package sensorserver.dataProviders.caching;

import com.google.inject.Inject;
import sensorserver.dataProviders.dao.IDataAccessor;
import sensorserver.dataProviders.dao.Identifiable;
import sensorserver.events.Event;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DaoCache<I, T extends Identifiable<I>> implements IDaoCache<I, T> {
    private Map<I, T> _cache;
    private IDataAccessor<I, T> _dao;
    // TODO: 7/19/2018 add event of reading / writing from the dao

    @Inject
    public DaoCache(IDataAccessor<I, T> dao) {
        this.setDao(dao);
        _cache = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized Optional<T> retrieve(I key) {
        if (_cache.containsKey(key)) {
            return Optional.ofNullable(_cache.get(key));
        } else {
            Optional<T> optionalResult = _dao.fetchById(key);

            if (optionalResult.isPresent()) {
                T daoEntity = optionalResult.get();
                this._cache.put(daoEntity.getId(), daoEntity);
            }

            return optionalResult;
        }
    }

    @Override
    public synchronized void cache(I key, T record) {
        this._cache.put(key, record);
        this.getDao().add(record);
    }

    @Override
    public synchronized void cache(T record) {
        this.cache(record.getId(), record);
    }

    @Override
    public synchronized void setDao(IDataAccessor<I, T> dao) {
        if (dao == null) {
            throw new IllegalArgumentException("dao cannot be null");
        }

        _dao = dao;
    }

    @Override
    public synchronized IDataAccessor<I, T> getDao() {
        return _dao;
    }
}
