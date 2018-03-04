package bl.informationEngine;

import bl.sensors.SensorEventData;

public class InformingEngine implements Hub<SensorEventData>, InformingManager {
	// Data members
	
	
	// Ctor
	
	
	// Methods
	
	
	// API
	
	@Override
	public void recieve(SensorEventData eventData) {
		
	}

	@Override
	public void registerObserver(Hub<ActivityEvent> observer) {
		
	}

	@Override
	public void ungisterObserver(Hub<ActivityEvent> observer) {
		
	}
}
