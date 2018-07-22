package whosHome.common.dataProviders.db;

import org.hibernate.SessionFactory;
import whosHome.common.dataProviders.ISensorTypesMetadataDao;
import whosHome.common.models.SensorTypeMetadata;

public class SensorTypesMetadataDbDao
        extends AbstractDatabaseDao<Integer, SensorTypeMetadata>
        implements ISensorTypesMetadataDao {
    public SensorTypesMetadataDbDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
