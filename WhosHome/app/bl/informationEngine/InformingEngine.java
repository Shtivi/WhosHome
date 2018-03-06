package bl.informationEngine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import bl.identifiers.IdentificationCenter;
import bl.identifiers.IdentificationData;
import bl.sensors.EventType;
import bl.sensors.ISensor;
import bl.sensors.SensorEventData;
import bl.sensors.SensorType;
import bl.sensors.SensorsFactory;
import models.Person;
import utils.GsonParser;

@Singleton
public class InformingEngine implements Hub<SensorEventData>, InformingManager {
	// Data members
	private List<Hub<ActivityEvent>> observers;
	private Map<Integer, ISensor> sensors;
	private SensorsFactory sensorsFactory;
	private Map<IdentificationData, ActivityEvent> presentEntities;
	
	// Ctor
	public InformingEngine() {
		// Initialize data members
		this.setObservers(new ArrayList<Hub<ActivityEvent>>());
		this.setSensors(new HashMap<Integer, ISensor>());
		this.setSensorsFactory(new SensorsFactory());
		this.setPresentEntities(new HashMap<IdentificationData, ActivityEvent>());
		
		// Create sensors
		this.attachSensor(this.getSensorsFactory().createSensor(SensorType.NETWORK, this));
		
		// Start sensors
		this.getSensors().values().forEach(sensor -> {
			try {
				sensor.start();
			} catch (Exception e) {
				System.err.println("Sensor " + sensor.toString() + " failed to start: " + e.getMessage());
			}
		});
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
	
	private void setPresentEntities(Map<IdentificationData, ActivityEvent> presentEntities) {
		this.presentEntities = presentEntities;
	}
	
	public Map<IdentificationData, ActivityEvent> presentEntities() {
		return this.presentEntities;
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
		// Received an event from the sensor, now find out who it is and create an activity event
		Person subject = IdentificationCenter.instance().identify(eventData.getIdentificationData());
		ActivityEvent event = new ActivityEvent(eventData, subject);
		
		// Update present entities holder
		if (eventData.getEventType() == EventType.IN) {
			this.presentEntities().put(eventData.getIdentificationData(), event);
		} else {
			this.presentEntities().remove(eventData.getIdentificationData());
		}
		
		// Report the event to observers
		this.report(event);
	}

	@Override
	public void registerObserver(Hub<ActivityEvent> observer) {
		this.getObservers().add(observer);
	}

	@Override
	public void ungisterObserver(Hub<ActivityEvent> observer) {
		this.getObservers().remove(observer);
	}
	
	@Override
	public Collection<ActivityEvent> getPresentEntities() {
		return this.presentEntities().values();
	}
	
	@Override
	public void shutSensorsDown() {
		this.getSensors().values().forEach((sensor) -> sensor.stop());
	}
}
