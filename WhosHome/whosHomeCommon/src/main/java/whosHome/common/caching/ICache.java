package whosHome.common.caching;

import whosHome.common.Identifiable;
import whosHome.common.events.Event;

import java.util.Optional;

public interface ICache<K, V extends Identifiable<K>> {
    Optional<V> retrieve(K key);
    void cache(K key, V record);
    void cache(V record);
    Event<cachedDataChangedEventArgs<V>> onItemCached();
}
