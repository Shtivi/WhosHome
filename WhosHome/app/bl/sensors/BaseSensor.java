package bl.sensors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bl.identifiers.IdentificationData;
import bl.informationEngine.Hub;
import exceptions.InvalidSensorActionException;

public abstract class BaseSensor implements ISensor {
	// Data members
	private int ID;
	private Hub<SensorEventData> hub;
	private SensorState state;
	
	// Ctor
	public BaseSensor(int ID, Hub<SensorEventData> hub) {
		this.setID(ID);
		this.setHub(hub);
	}
	
	// Access methods
	
	@Override
	public int getID() {
		return this.ID;
	}
	
	private void setID(int ID) {
		this.ID = ID;
	}
	
	@JsonIgnore
	public Hub<SensorEventData> getHub() {
		return this.hub;
	}
	
	public void setHub(Hub<SensorEventData> hub) {		
		if (hub != null) {
			this.setSensorState(SensorState.READY, null);
		} else {
			this.setSensorState(SensorState.NOT_READY, "Hub detached");
		}
		
		this.hub = hub;
	}
	
	public boolean isReady() {
		return (this.ready());
	}
	
	public SensorState getSensorState() {
		return this.state;
	}
	
	protected void setSensorState(SensorState state, String reason) {
		// If this is the first init
		if (this.state != null) {
			this.getHub().sensorStateChanged(new SensorStateChangedEvent(this, state, this.getSensorState(), reason));
		}
		
		this.state = state;
	}
	
	// API
	
	public void detachHub() {
		this.stop();
		this.setHub(null);
	}
	
	public boolean ready() {
		return (this.state == SensorState.READY);
	}
	
	@Override
	public void start() throws InvalidSensorActionException {
		if (!this.ready()) {
			throw new InvalidSensorActionException("Sensor is not ready yet");
		}
	}
	
	@Override
	public String toString() {
		return ("#" + this.getID() + " " + this.getSensorType().toString());
	}
	
	// Methods
	
	protected void report(SensorEventData eventData) {
		this.getHub().recieve(eventData);
	}
	
	protected SensorEventData createEventData(EventType eventType, IdentificationData identifyingData) {
		return new SensorEventData(eventType, identifyingData, this);
	}
}
