package bl.informationEngine;

import bl.identifiers.IdentificationData;
import bl.sensors.EventType;
import bl.sensors.ISensor;
import bl.sensors.SensorEventData;
import bl.sensors.SensorType;
import models.Person;

public class ActivityEvent extends InformingEvent {
	// Data members
	private Person subject;
	private int sensorID;
	private String sensorName;
	private SensorType sensorType;
	private String identificationData;
	
	// Ctor
	
	public ActivityEvent(SensorEventData sourceEvent, Person subject) {
		super(sourceEvent.getEventType(), sourceEvent.getTime());
		this.setSensorID(sourceEvent.getSensor().getID());
		this.setSensorName(sourceEvent.getSensor().getName());
		this.setSensorType(sourceEvent.getSensor().getSensorType());
		this.setSubject(subject);
		this.setIdentificationData(sourceEvent.getIdentificationData().toString());
	}
	
	// Access methods
	
	public Person getSubject() {
		return this.subject;
	}
	
	private void setSubject(Person subject) {
		this.subject = subject;
	}
	
	public String getIdentificationData() {
		return this.identificationData;
	}
	
	public void setIdentificationData(String data) {
		this.identificationData = data;
	}
	
	public int getSensorID() {
		return sensorID;
	}
	
	public void setSensorID(int sensorID) {
		this.sensorID = sensorID;
	}
	
	public String getSensorName() {
		return sensorName;
	}
	
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	
	public SensorType getSensorType() {
		return sensorType;
	}
	
	public void setSensorType(SensorType type) {
		this.sensorType = type;
	}
}
