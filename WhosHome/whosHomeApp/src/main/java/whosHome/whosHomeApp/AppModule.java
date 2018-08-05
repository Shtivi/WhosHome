package whosHome.whosHomeApp;

import com.google.gson.reflect.TypeToken;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.typesafe.config.Config;
import whosHome.common.dataProviders.IDataProvider;
import whosHome.common.dataProviders.ISensorConnectionsMetadataDao;
import whosHome.common.dataProviders.ISensorTypesMetadataDao;
import whosHome.common.dataProviders.db.Hibernate;
import whosHome.common.dataProviders.db.SensorConnectionsMetadataDbDao;
import whosHome.common.dataProviders.db.SensorTypesMetadataDbDao;
import whosHome.whosHomeApp.dataAccess.IPeopleDao;
import whosHome.whosHomeApp.dataAccess.agents.PeopleServiceAgent;
import whosHome.whosHomeApp.engine.sensors.ISensorConnectionsFactory;
import whosHome.whosHomeApp.engine.sensors.SensorConnectionsFactory;

public class AppModule extends AbstractModule {
    private Config _config;
    private Application.Environment _env;

    public AppModule(Config config, Application.Environment environment) {
        _config = config;
        _env = environment;
    }

    @Override
    public void configure() {
        bind(String.class)
                .annotatedWith(Names.named("peopleServiceUrl"))
                .toInstance(_config.getString("peopleService.url"));
        bind(String.class)
                .annotatedWith(Names.named("peopleApiPath"))
                .toInstance(_config.getString("peopleService.peopleApiPath"));
        bind(IPeopleDao.class).to(PeopleServiceAgent.class);
        bind(ISensorConnectionsMetadataDao.class).to(SensorConnectionsMetadataDbDao.class);
        bind(ISensorTypesMetadataDao.class).to(SensorTypesMetadataDbDao.class);
        bind(ISensorConnectionsFactory.class).to(SensorConnectionsFactory.class);

        if (_env == Application.Environment.PROD) {
            configureProd();
        } else if (_env == Application.Environment.DEBUG) {
            configureDebug();
        }
    }

    @Provides @Singleton
    private Hibernate provideHibernate() {
        String username = _config.getString("mysql.username");
        String password = _config.getString("mysql.password");
        String url = _config.getString("mysql.url");
        String dbname = _config.getString("mysql.dbname");

        Hibernate hibernate = new Hibernate.Builder()
                .withServerUrl(url, dbname)
                .withUsername(username)
                .withPassword(password)
                .build();
        return hibernate;
    }

    private void configureDebug() {

    }

    private void configureProd() {

    }
}
