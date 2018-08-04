package whosHome.whosHomeApp.dataAccess.db;

import org.hibernate.SessionFactory;
import whosHome.common.dataProviders.db.AbstractDatabaseDao;
import whosHome.whosHomeApp.dataAccess.IDevicesDao;
import whosHome.whosHomeApp.models.Device;

import java.util.Collection;

public class DevicesDbDao extends AbstractDatabaseDao<String, Device> implements IDevicesDao {
    public DevicesDbDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Collection<Device> fetchByOwnerID(String ownerID) {
        return null;
    }
}
