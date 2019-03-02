package sensorserver.dataProviders.caching;

import sensorserver.dataProviders.dao.Identifiable;

import java.util.Optional;

public interface ICache<K, V extends Identifiable<K>> {
    Optional<V> retrieve(K key);
    void cache(K key, V record);
    void cache(V record);
}
