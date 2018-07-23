package whosHome.whosHomeApp.dataAccess.db;

import org.hibernate.SessionFactory;
import whosHome.common.dataProviders.db.AbstractDatabaseDao;
import whosHome.whosHomeApp.models.Device;

public class DevicesDbDao extends AbstractDatabaseDao<Integer, Device> {
    public DevicesDbDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
