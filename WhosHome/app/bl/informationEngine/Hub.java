package bl.informationEngine;

import bl.sensors.SensorEventData;

public interface Hub<T extends InformingEvent> {
	void recieve(T eventData);
}
