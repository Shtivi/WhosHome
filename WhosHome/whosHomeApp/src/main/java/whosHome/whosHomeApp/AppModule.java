package whosHome.whosHomeApp;

import com.google.inject.AbstractModule;
import com.typesafe.config.Config;

public class AppModule extends AbstractModule {
    private Config _config;

    public AppModule(Config config) {
        _config = config;
    }

    @Override
    public void configure() {

    }
}
