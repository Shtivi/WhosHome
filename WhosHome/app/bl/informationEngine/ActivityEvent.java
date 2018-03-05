package bl.informationEngine;

import bl.sensors.EventType;
import bl.sensors.ISensor;
import bl.sensors.SensorEventData;
import models.Person;

public class ActivityEvent extends InformingEvent {
	// Data members
	private Person subject;
	private ISensor sensor; 
	
	// Ctor
	
	public ActivityEvent(SensorEventData sourceEvent, Person subject) {
		super(sourceEvent.getEventType(), sourceEvent.getTime());
		this.setSensor(sourceEvent.getSensor());
		this.setSubject(subject);
	}
	
	public ActivityEvent(EventType eventType, Person subject, ISensor sensor) {
		super(eventType);
		this.setSubject(subject);
		this.setSensor(sensor);
	}
	
	// Access methods
	public Person getSubject() {
		return this.subject;
	}
	
	private void setSubject(Person subject) {
		this.subject = subject;
	}

	public ISensor getSensor() {
		return this.sensor;
	}
	
	private void setSensor(ISensor sensor) {
		this.sensor = sensor;
	}
}
