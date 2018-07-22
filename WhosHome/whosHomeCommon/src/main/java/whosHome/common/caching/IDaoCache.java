package whosHome.common.caching;

import whosHome.common.Identifiable;
import whosHome.common.dataProviders.IDataProvider;

public interface IDaoCache<I, V extends Identifiable<I>> extends ICache<I, V> {
    void setDao(IDataProvider<I, V> dao);
    IDataProvider<I, V> getDao();
}
