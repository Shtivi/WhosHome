package whosHome.common.caching;

import whosHome.common.Identifiable;
import whosHome.common.dataProviders.IDataProvider;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DaoCache<I, T extends Identifiable<I>> implements IDaoCache<I, T> {
    private Map<I, T> _cache;
    private IDataProvider<I, T> _dao;
    // TODO: 7/19/2018 add event of reading / writing from the dao

    public DaoCache(IDataProvider<I, T> dao) {
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
                this._cache.put(daoEntity.getID(), daoEntity);
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
        this.cache(record.getID(), record);
    }

    @Override
    public synchronized void setDao(IDataProvider<I, T> dao) {
        if (dao == null) {
            throw new IllegalArgumentException("dao cannot be null");
        }

        _dao = dao;
    }

    @Override
    public synchronized IDataProvider<I, T> getDao() {
        return _dao;
    }
}
