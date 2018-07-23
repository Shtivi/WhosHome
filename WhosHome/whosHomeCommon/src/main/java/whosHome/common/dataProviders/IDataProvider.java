package whosHome.common.dataProviders;

import java.util.Collection;
import java.util.Optional;

public interface IDataProvider<I, T> {
    Collection<T> fetchAll();
    Optional<T> fetchById(I id);
    void add(T record);
    void add(Iterable<T> records);
    void update(T record);
    void delete(I id);
    void delete(Collection<I> ids);
}
