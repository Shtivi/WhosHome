package bl.informationEngine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import bl.identifiers.IdentificationCenter;
import bl.sensors.EventType;
import bl.sensors.ISensor;
import bl.sensors.SensorEventData;
import bl.sensors.SensorState;
import bl.sensors.SensorStateChangedEvent;
import bl.sensors.SensorType;
import bl.sensors.SensorsFactory;
import models.Person;

@Singleton
public class InformingEngine implements Hub<SensorEventData>, InformingManager {
	// Data members
	private List<Hub<ActivityEvent>> observers;
	private Map<Integer, ISensor> sensors;
	private SensorsFactory sensorsFactory;
	private PresenceHolder presenceHolder;
	
	// Ctor
	public InformingEngine() {
		// Initialize data members
		this.setObservers(new ArrayList<Hub<ActivityEvent>>());
		this.setSensors(new HashMap<Integer, ISensor>());
		this.setSensorsFactory(new SensorsFactory());
		this.setPresenceHolder(new PresenceHolder());
		
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
	
	private void setPresenceHolder(PresenceHolder holder) {
		this.presenceHolder = holder;
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
		
		// Update present entities holder with the event
		if (eventData.getEventType() == EventType.IN) {
			this.getPresenceHolder().in(event);
		} else if (eventData.getEventType() == EventType.OUT) {
			this.getPresenceHolder().out(event);
		}
		
		// Report the event to observers
		this.report(event);
	}
	
	@Override
	public void sensorStateChanged(SensorStateChangedEvent stateEvent) {
		// Log
		System.out.println(String.format("Sensor state changed: [#%d %s] from '%s' to '%s'. Reason: '%s'", 
				stateEvent.getSensorID(), 
				stateEvent.getSensorName(), 
				stateEvent.getOldState().toString(), 
				stateEvent.getNewState().toString(),
				stateEvent.getReason() == null ? "null" : stateEvent.getReason()));
	
		// Report to observers
		this.getObservers().forEach((observer) -> {
			observer.sensorStateChanged(stateEvent);
		});
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
	public void shutSensorsDown() {
		this.getSensors().values().forEach((sensor) -> sensor.stop());
	}
	
	@Override
	public PresenceHolder getPresenceHolder() {
		return this.presenceHolder;
	}

	@Override
	public Collection<ISensor> getAttachedSensors() {
		return this.getSensors().values();
	}
}
