package whosHome.whosHomeApp.dataAccess.db;

import org.hibernate.SessionFactory;
import whosHome.common.dataProviders.db.AbstractDatabaseDao;
import whosHome.common.dataProviders.db.Hibernate;
import whosHome.whosHomeApp.dataAccess.IDevicesDao;
import whosHome.whosHomeApp.models.Device;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class DevicesDbDao extends AbstractDatabaseDao<String, Device> implements IDevicesDao {
    @Inject
    public DevicesDbDao(Hibernate hibernate) {
        super(hibernate);
    }

    @Override
    public Collection<Device> fetchByOwnerID(String ownerID) {
        return null;
    }
}
