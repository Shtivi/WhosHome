package bl.informationEngine;

public interface InformingManager {
	void registerObserver(Hub<ActivityEvent> observer);
	void ungisterObserver(Hub<ActivityEvent> observer);
}
