package bl.informationEngine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import bl.identifiers.IdentificationData;

public class PresenceHolder {
	// Data members
	private Map<String, ActivityEvent> unknownEntities;
	private Map<String, ActivityEvent> identifiedEntities;
	
	// Ctor
	public PresenceHolder() {
		this.setIdentifiedEntities(new HashMap<String, ActivityEvent>());
		this.setUnknownEntities(new HashMap<String, ActivityEvent>());
	}
	
	// Access methods
	
	public Map<String, ActivityEvent> getUnknownEntities() {
		return unknownEntities;
	}
	
	private void setUnknownEntities(Map<String, ActivityEvent> unknownEntities) {
		this.unknownEntities = unknownEntities;
	}
	
	public Map<String, ActivityEvent> getIdentifiedEntities() {
		return identifiedEntities;
	}
	
	private void setIdentifiedEntities(Map<String, ActivityEvent> identifiedEntities) {
		this.identifiedEntities = identifiedEntities;
	}
	
	// API
	
	public void in(ActivityEvent event) {
		if (event.getSubject() == null) {
			this.getUnknownEntities().put(event.getIdentificationData(), event);
		} else {
			this.getIdentifiedEntities().put(event.getSubject().getID(), event);
		}
	}
	
	public void out(ActivityEvent event) {
		if (event.getSubject() == null) {
			this.getUnknownEntities().remove(event.getIdentificationData());
		} else {
			this.getIdentifiedEntities().remove(event.getSubject().getID());
		}
	}
	
//	public Collection<ActivityEvent> identifiedEntities() {
//		return this.getIdentifiedEntities().values();
//	}
//	
//	public Collection<ActivityEvent> unknownEntities() {
//		return this.getUnknownEntities().values();
//	}
}
