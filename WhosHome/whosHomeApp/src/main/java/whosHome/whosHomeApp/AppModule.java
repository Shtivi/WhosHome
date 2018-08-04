package whosHome.whosHomeApp;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.typesafe.config.Config;
import whosHome.whosHomeApp.dataAccess.IPeopleDao;
import whosHome.whosHomeApp.dataAccess.agents.PeopleServiceAgent;

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
    }
}
