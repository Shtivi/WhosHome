package bl.informationEngine;

import java.util.Date;

import bl.sensors.EventType;

public abstract class InformingEvent {
	// Data members
	private EventType eventType;
	private Date time;
	
	// Ctor
	public InformingEvent(EventType eventType) {
		this.setTime(new Date());
		this.setEventType(eventType);
	}
	
	// Access methods
	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}

	public EventType getEventType() {
		return eventType;
	}
	
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
}
