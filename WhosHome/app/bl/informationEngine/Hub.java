package bl.informationEngine;

import bl.sensors.ISensor;
import bl.sensors.SensorEventData;
import bl.sensors.SensorState;
import bl.sensors.SensorStateChangedEvent;

public interface Hub<T extends InformingEvent> {
	void recieve(T eventData);
	void sensorStateChanged(SensorStateChangedEvent event);
}
