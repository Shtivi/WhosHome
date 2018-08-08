package whosHome.whosHomeApp.infra;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import whosHome.whosHomeApp.engine.WhosHomeEngine;

public class WhosHomeEngineInitializer implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        WhosHomeEngine engine = applicationStartedEvent.getApplicationContext().getBean(WhosHomeEngine.class);
        engine.initialize().start();
    }
}
