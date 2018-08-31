package whosHome.common.dataProviders.db;

import com.google.inject.Inject;
import org.hibernate.SessionFactory;
import whosHome.common.dataProviders.ISensorConnectionsMetadataDao;
import whosHome.common.models.SensorConnectionMetadata;

public class SensorConnectionsMetadataDbDao
        extends AbstractDatabaseDao<Integer, SensorConnectionMetadata>
        implements ISensorConnectionsMetadataDao {
    @Inject
    public SensorConnectionsMetadataDbDao(Hibernate hibernate) {
        super(hibernate);
    }
}
