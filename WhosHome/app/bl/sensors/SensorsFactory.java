package bl.sensors;

import java.util.HashMap;
import java.util.Map;

import bl.informationEngine.Hub;
import bl.sensors.lan.LanSensor;

public class SensorsFactory {
	// Static members
	private static int totalSensors = 0;
	
	// Data members
	private Map<SensorType, SensorCreator> creators;
	
	// Ctor
	public SensorsFactory() {
		this.creators = new HashMap<SensorType, SensorCreator>();
		
		// Fill creators map
		creators.put(SensorType.NETWORK, (id, hub) -> new LanSensor(id, hub));
	}
	
	// Methods

	private int generateID() {
		return (++totalSensors);
	}
	
	// API
	
	public ISensor createSensor(SensorType sensorType, Hub<SensorEventData> hub) {
		return this.creators.get(sensorType).create(this.generateID(), hub);
	}
	
	// SensorCreator functional interface
	public interface SensorCreator {
		ISensor create(int ID, Hub<SensorEventData> hub);
	}
}
