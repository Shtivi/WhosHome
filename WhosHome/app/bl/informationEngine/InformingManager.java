package bl.informationEngine;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import bl.identifiers.IdentificationData;
import models.Person;

public interface InformingManager {
	void registerObserver(Hub<ActivityEvent> observer);
	void ungisterObserver(Hub<ActivityEvent> observer);
	PresenceHolder getPresenceHolder();
	void shutSensorsDown();
}
