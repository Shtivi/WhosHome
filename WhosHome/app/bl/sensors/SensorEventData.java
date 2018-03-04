package bl.sensors;

import java.util.Date;

import bl.informationEngine.InformingEvent;

public class SensorEventData extends InformingEvent {
	// Data members
	private IdentifyingData identifyingData;
	private ISensor sensor;
	
	// Ctor
	public SensorEventData(EventType eventType, IdentifyingData identifyingData, ISensor sensor) {
		super(eventType);
		this.setSensor(sensor);
		this.setIdentifyingData(identifyingData);
	}
	
	// Access methods
	
	public IdentifyingData getIdentifyingData() {
		return identifyingData;
	}
	
	public void setIdentifyingData(IdentifyingData identifyingData) {
		this.identifyingData = identifyingData;
	}
	
	public ISensor getSensor() {
		return sensor;
	}
	
	public void setSensor(ISensor sensor) {
		this.sensor = sensor;
	}
	
}
