package bl.identifiers;

import java.util.HashMap;
import java.util.Map;

import bl.sensors.lan.NetworkIdentificationData;
import models.Person;

public class IdentificationCenter {
	// Singleton
	private static IdentificationCenter _instance = null;
	
	public static IdentificationCenter instance() {
		if (_instance == null) {
			_instance = new IdentificationCenter();
		}
		
		return (_instance);
	}
	
	// Data members
	private Map<Class, Identifier> identifiers;
	
	// Ctor
	private IdentificationCenter() {
		identifiers(new HashMap<Class, Identifier>());
		
		// Fill identifiers
		this.identifiers().put(NetworkIdentificationData.class, new NetworkIdentifier());
	}
	
	// Access methods
	
	private Map<Class, Identifier> identifiers() {
		return this.identifiers;
	}
	
	private void identifiers(Map<Class, Identifier> identifiers) {
		this.identifiers = identifiers;
	}
	
	// API
	
	public Person identify(IdentificationData identificationData) {
		// Extract the matching identifier according to the type of the identification data,
		// execute the identification and return the result.
		return (this.identifiers().get(identificationData.getClass()).identify(identificationData));
	}
}
