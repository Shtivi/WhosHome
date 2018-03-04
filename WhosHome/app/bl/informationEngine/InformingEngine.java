package bl.informationEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import bl.sensors.ISensor;
import bl.sensors.SensorEventData;
import bl.sensors.SensorType;
import bl.sensors.SensorsFactory;

@Singleton
public class InformingEngine implements Hub<SensorEventData>, InformingManager {
	// Data members
	private List<Hub<ActivityEvent>> observers;
	private Map<Integer, ISensor> sensors;
	private SensorsFactory sensorsFactory;
	
	// Ctor
	public InformingEngine() {
		// Initialize data members
		this.setObservers(new ArrayList<Hub<ActivityEvent>>());
		this.setSensors(new HashMap<Integer, ISensor>());
		this.setSensorsFactory(new SensorsFactory());
		
		// Create sensors
		this.attachSensor(this.getSensorsFactory().createSensor(SensorType.NETWORK, this));
		
		// Start sensors
		this.getSensors().values().forEach(sensor -> sensor.start());
	}
	
	// Access methods
	
	private List<Hub<ActivityEvent>> getObservers() {
		return this.observers;
	}
	
	private void setObservers(List<Hub<ActivityEvent>> observers) {
		this.observers = observers;
	}

	private Map<Integer, ISensor> getSensors() {
		return this.sensors;
	}
	
	private void setSensors(Map<Integer, ISensor> sensors) {
		this.sensors = sensors;
	}
	
	private SensorsFactory getSensorsFactory() {
		return this.sensorsFactory;
	}
	
	private void setSensorsFactory(SensorsFactory factory) {
		this.sensorsFactory = factory;
	}
	
	// Methods
	
	private void report(ActivityEvent event) {
		this.getObservers().forEach((observer) -> observer.recieve(event));
	}
	
	private void attachSensor(ISensor sensor) {
		if (!this.getSensors().containsKey(sensor.getID())) {
			this.getSensors().put(sensor.getID(), sensor);
			sensor.setHub(this);
		}
	}
	
	private void detachSensor(ISensor sensor) {
		if (this.getSensors().containsKey(sensor.getID())) {			
			// Detach the sensor from the hub
			sensor.detachHub();
			
			// Remove from the registered sensors
			this.getSensors().remove(sensor.getID());
		}
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
