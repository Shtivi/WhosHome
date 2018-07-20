package sensorserver.dataProviders.caching;

import sensorserver.dataProviders.dao.Identifiable;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCache<I, T extends Identifiable<I>> implements ICache<I, T> {
    private Map<I, T> _cache;
    
    public MemoryCache() {
        _cache = new ConcurrentHashMap<>();
    }

    // TODO: 7/19/2018 implement
    
    @Override
    public Optional<T> retrieve(I key) {
        return Optional.empty();
    }

    @Override
    public void cache(I key, T record) {

    }

    @Override
    public void cache(T record) {

    }
}
