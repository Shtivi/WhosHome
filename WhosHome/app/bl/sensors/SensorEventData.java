package bl.sensors;

import java.util.Date;

import bl.identifiers.IdentificationData;
import bl.informationEngine.InformingEvent;

public class SensorEventData extends InformingEvent {
	// Data members
	private IdentificationData identifyingData;
	private ISensor sensor;
	
	// Ctor
	public SensorEventData(EventType eventType, IdentificationData identifyingData, ISensor sensor) {
		super(eventType);
		this.setSensor(sensor);
		this.setIdentificationData(identifyingData);
	}
	
	// Access methods
	
	public IdentificationData getIdentificationData() {
		return identifyingData;
	}
	
	public void setIdentificationData(IdentificationData identifyingData) {
		this.identifyingData = identifyingData;
	}
	
	public ISensor getSensor() {
		return sensor;
	}
	
	public void setSensor(ISensor sensor) {
		this.sensor = sensor;
	}
	
}
