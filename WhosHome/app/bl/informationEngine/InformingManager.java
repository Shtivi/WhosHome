package bl.informationEngine;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import bl.identifiers.IdentificationData;
import bl.sensors.ISensor;
import exceptions.InvalidSensorActionException;
import models.Person;

public interface InformingManager {
	void registerObserver(Hub<ActivityEvent> observer);
	void ungisterObserver(Hub<ActivityEvent> observer);
	PresenceHolder getPresenceHolder();
	void shutSensorsDown();
	void startSensor(int sensorID) throws InvalidSensorActionException;
	void stopSensor(int sensorID) throws InvalidSensorActionException;
	void toggleSensor(int sensorID) throws InvalidSensorActionException;
	Collection<ISensor> getAttachedSensors();
}
