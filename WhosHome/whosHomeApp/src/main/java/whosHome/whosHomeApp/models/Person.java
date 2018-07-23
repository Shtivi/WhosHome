package whosHome.whosHomeApp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import whosHome.common.Identifiable;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person implements Identifiable<String> {
    private String ID;
    private String firstname;
    private String lastname;
    private String facebookID;
    private String phoneNo;
    private Date insertionDate;

    public Person() {}

    @Override
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Date getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(Date insertionDate) {
        this.insertionDate = insertionDate;
    }
}
