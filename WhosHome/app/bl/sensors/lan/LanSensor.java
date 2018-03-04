package bl.sensors.lan;

import bl.informationEngine.Hub;
import bl.sensors.BaseSensor;
import bl.sensors.ISensor;
import bl.sensors.SensorType;

public class LanSensor extends BaseSensor {
	// Ctor
	public LanSensor(int ID, Hub hub) {
		super(ID, hub);
	}
	
	// API

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SensorType getSensorType() {
		return (SensorType.NETWORK);
	}
}
