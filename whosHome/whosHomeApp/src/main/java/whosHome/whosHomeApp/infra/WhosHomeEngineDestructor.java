package whosHome.whosHomeApp.infra;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import whosHome.whosHomeApp.engine.WhosHomeEngine;

public class WhosHomeEngineDestructor implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        WhosHomeEngine engine = contextClosedEvent.getApplicationContext().getBean(WhosHomeEngine.class);
        engine.stop();
    }
}
