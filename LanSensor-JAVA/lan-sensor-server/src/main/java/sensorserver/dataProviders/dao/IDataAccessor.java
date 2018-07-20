package sensorserver.dataProviders.dao;

import java.util.Collection;
import java.util.Optional;

public interface IDataAccessor<I, T> {
    Iterable<T> fetchAll();
    Optional<T> fetchById(I id);
    void add(T record);
    void add(Iterable<T> records);
    void update(T record);
    void delete(I id);
    void delete(Collection<I> ids);
}
