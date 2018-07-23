package sensorserver.dataProviders.mysql;

import com.google.inject.Inject;
import org.hibernate.SessionFactory;
import sensorserver.dataProviders.dao.IVendorsDao;
import sensorserver.models.Vendor;

public class MysqlVendorsDao extends AbstractMysqlDao<String, Vendor> implements IVendorsDao {
    @Inject
    public MysqlVendorsDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
