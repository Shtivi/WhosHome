package models;

public class Person {
	// Data members
	private String ID;
	private String firstName;
	private String lastName;
	private String macAddress;
	private String facebookID;
	
	// Ctor
	public Person() {
	}
	
	public Person(String ID, String firstName, String lastName, String macAddress, String facebookID) {
		this.setID(ID);
		this.setFacebookID(facebookID);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setMacAddress(macAddress);
	}
	
	// Access methods
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getFacebookID() {
		return facebookID;
	}
	public void setFacebookID(String facebookID) {
		this.facebookID = facebookID;
	}	
}
