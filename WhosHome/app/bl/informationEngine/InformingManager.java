package bl.informationEngine;

import java.util.Map;

import bl.identifiers.IdentificationData;
import models.Person;

public interface InformingManager {
	void registerObserver(Hub<ActivityEvent> observer);
	void ungisterObserver(Hub<ActivityEvent> observer);
	Map<IdentificationData, Person> getPresentEntities();
}
