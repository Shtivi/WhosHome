package whosHome.common.dataProviders.db;

import com.google.inject.Inject;
import org.hibernate.SessionFactory;
import whosHome.common.dataProviders.ISensorTypesMetadataDao;
import whosHome.common.models.SensorTypeMetadata;

public class SensorTypesMetadataDbDao
        extends AbstractDatabaseDao<Integer, SensorTypeMetadata>
        implements ISensorTypesMetadataDao {
    @Inject
    public SensorTypesMetadataDbDao(Hibernate hibernate) {
        super(hibernate);
    }
}
