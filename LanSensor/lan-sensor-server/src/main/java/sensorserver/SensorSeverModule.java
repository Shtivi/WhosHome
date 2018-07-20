package sensorserver;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.typesafe.config.Config;
import org.hibernate.SessionFactory;
import sensorserver.dataProviders.caching.DaoCache;
import sensorserver.dataProviders.caching.ICache;
import sensorserver.dataProviders.caching.MemoryCache;
import sensorserver.dataProviders.dao.IDataAccessor;
import sensorserver.dataProviders.dao.IVendorsDao;
import sensorserver.dataProviders.dao.Identifiable;
import sensorserver.dataProviders.mySql.MysqlVendorsDao;
import sensorserver.dataProviders.vendors.*;
import sensorserver.engine.ArpTable;
import sensorserver.engine.entities.IEntitiesHolder;
import sensorserver.engine.entities.LanEntitiesHolder;
import sensorserver.engine.workers.IWorkersFactory;
import sensorserver.engine.workers.WorkersFactory;
import sensorserver.models.Vendor;
import sensorserver.server.ISensorService;
import sensorserver.server.SensorService;
import sensorserver.utils.HibernateUtils;
import sensorserver.utils.mocks.ArpTableMock;
import sensorserver.utils.mocks.VendorsProviderMock;
import sensorserver.utils.mocks.WorkersFactoryMock;

public class SensorSeverModule extends AbstractModule {
    private Config _config;
    private SensorRuntimeContext.Environment _environment;
    private String _operatingSystem;

    public SensorSeverModule(Config config, SensorRuntimeContext.Environment environment, String opeartingSystem) {
        _config = config;
        _environment = environment;
        _operatingSystem = opeartingSystem;
    }

    @Override
    protected void configure() {
        if (_environment == SensorRuntimeContext.Environment.DEBUG) {
            initializeDebug();
        } else if (_environment == SensorRuntimeContext.Environment.PROD) {
            initializeProd();
        }

        bind(String.class)
                .annotatedWith(Names.named("os"))
                .toInstance(_operatingSystem);

        String arpCmd = _config.getString("network.arp-command." + _operatingSystem);
        bind(String.class)
                .annotatedWith(Names.named("arpCmd"))
                .toInstance(arpCmd);

        String vendorsCachePath = _config.getString("vendorsProvider.cachePath");
        bind(String.class)
                .annotatedWith(Names.named("vendorsCachePath"))
                .toInstance(vendorsCachePath);

        String vendorsApiUrl = _config.getString("vendorsProvider.url");
        bind(String.class)
                .annotatedWith(Names.named("vendorsApiUrl"))
                .toInstance(vendorsApiUrl);

        int serverPort = _config.getInt("server.port");
        bind(Integer.class)
                .annotatedWith(Names.named("serverPort"))
                .toInstance(serverPort);

        bind(ISensorService.class).to(SensorService.class);
        bind(IEntitiesHolder.class).to(LanEntitiesHolder.class);

        bind(SessionFactory.class).toInstance(HibernateUtils.getSessionFactory());
        bind(new TypeLiteral<IDataAccessor<String, Vendor>>(){}).to(MysqlVendorsDao.class);

        bind(new TypeLiteral<ICache>(){}).to(new TypeLiteral<MemoryCache>(){});
    }

    private void initializeDebug() {
        bind(ArpTable.class).to(ArpTableMock.class);
        bind(IVendorsProvider.class).to(VendorsProviderMock.class);
        bind(IWorkersFactory.class).to(WorkersFactoryMock.class);
    }

    private void initializeProd() {
        bind(new TypeLiteral<ICache<String, Vendor>>(){}).to(new TypeLiteral<DaoCache<String, Vendor>>(){});
        bind(IVendorsDao.class).to(MysqlVendorsDao.class);
        bind(IVendorsProvider.class).to(Mac2VendorProvider.class);
        bind(IWorkersFactory.class).to(WorkersFactory.class);
    }
}
