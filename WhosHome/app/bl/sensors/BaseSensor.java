package bl.sensors;

import bl.identifiers.IdentificationData;
import bl.informationEngine.Hub;

public abstract class BaseSensor implements ISensor {
	// Data members
	private int ID;
	private Hub<SensorEventData> hub;
	
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
	
	public Hub<SensorEventData> getHub() {
		return this.hub;
	}
	
	public void setHub(Hub<SensorEventData> hub) {
		this.hub = hub;
	}
	
	// API
	
	public void detachHub() {
		this.setHub(null);
		this.stop();
	}
	
	// Methods
	
	protected void report(SensorEventData eventData) {
		this.getHub().recieve(eventData);
	}
	
	protected SensorEventData createEventData(EventType eventType, IdentificationData identifyingData) {
		return new SensorEventData(eventType, identifyingData, this);
	}
}
