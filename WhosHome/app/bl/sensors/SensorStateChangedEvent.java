package bl.sensors;

import bl.informationEngine.InformingEvent;

public class SensorStateChangedEvent extends InformingEvent {
	// Data members
	private int sensorID;
	private String sensorName;
	private SensorType sensorType;
	private SensorState oldState;
	private SensorState newState;
	private String reason;
	
	// Ctor
	
	public SensorStateChangedEvent(ISensor source, SensorState newState, SensorState oldState, String reason) {
		super(EventType.SYSTEM_EVENT);
		
		this.setSensorID(source.getID());
		this.setSensorName(source.getName());
		this.setSensorType(source.getSensorType());
		this.setOldState(oldState);
		this.setNewState(newState);
		this.setReason(reason);
	}
	
	// Access methods

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

	public void setSensorType(SensorType sensorType) {
		this.sensorType = sensorType;
	}

	public SensorState getOldState() {
		return oldState;
	}

	public void setOldState(SensorState oldState) {
		this.oldState = oldState;
	}

	public SensorState getNewState() {
		return newState;
	}

	public void setNewState(SensorState newState) {
		this.newState = newState;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
