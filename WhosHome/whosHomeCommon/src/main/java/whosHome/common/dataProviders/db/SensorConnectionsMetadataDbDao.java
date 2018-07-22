package whosHome.common.dataProviders.db;

import org.hibernate.SessionFactory;
import whosHome.common.dataProviders.ISensorConnectionsMetadataDao;
import whosHome.common.models.SensorConnectionMetadata;

public class SensorConnectionsMetadataDbDao
        extends AbstractDatabaseDao<Integer, SensorConnectionMetadata>
        implements ISensorConnectionsMetadataDao {
    public SensorConnectionsMetadataDbDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
