package bl.informationEngine;

import java.util.ArrayList;
import java.util.List;

import bl.sensors.SensorEventData;

public class InformingEngine implements Hub<SensorEventData>, InformingManager {
	// Data members
	private List<Hub<ActivityEvent>> observers;
	
	// Ctor
	public InformingEngine() {
		this.setObservers(new ArrayList<Hub<ActivityEvent>>());
	}
	
	// Access methods
	
	private List<Hub<ActivityEvent>> getObservers() {
		return this.observers;
	}
	
	private void setObservers(List<Hub<ActivityEvent>> observers) {
		this.observers = observers;
	}
	
	// Methods
	private void report(ActivityEvent event) {
		this.getObservers().forEach((observer) -> observer.recieve(event));
	}
	
	// API
	
	@Override
	public void recieve(SensorEventData eventData) {
		
	}

	@Override
	public void registerObserver(Hub<ActivityEvent> observer) {
		this.getObservers().add(observer);
	}

	@Override
	public void ungisterObserver(Hub<ActivityEvent> observer) {
		this.getObservers().remove(observer);
	}
}
