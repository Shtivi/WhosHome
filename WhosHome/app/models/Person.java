package models;

public class Person {
	// Data members
	private String ID;
	private String firstname;
	private String lastname;
	private String macAddress;
	private String facebookID;
	private String phoneNo;
	
	// Ctor
	public Person() {
	}
	
	public Person(String ID, String firstname, String lastname, String macAddress, String facebookID, String phoneNo) {
		this.setID(ID);
		this.setFacebookID(facebookID);
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setMacAddress(macAddress);
		this.setPhoneNo(phoneNo);
	}
	
	// Access methods
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstName) {
		this.firstname = firstName;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastName) {
		this.lastname = lastName;
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
	public String getPhoneNo() {
		return this.phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
