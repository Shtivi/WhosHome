package infra;

import com.google.inject.AbstractModule;

import bl.informationEngine.Hub;
import bl.informationEngine.InformingEngine;
import bl.informationEngine.InformingManager;

public class ComponentModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(InformingManager.class).to(InformingEngine.class);
	}

}
