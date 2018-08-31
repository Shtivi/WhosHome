package whosHome.whosHomeApp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import whosHome.common.Identifiable;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person implements Identifiable<String> {
    @JsonProperty(value = "_id") private String ID;
    private String firstname;
    private String lastname;
    private String facebookID;
    private String phoneNo;
    private Date insertionDate;

    public Person() {}

    public Person(Person other) {
        this.setID(other.getID());
        this.setInsertionDate(other.getInsertionDate());
        this.setLastname(other.getLastname());
        this.setFirstname(other.firstname);
        this.setFacebookID(other.facebookID);
        this.setPhoneNo(other.phoneNo);
    }

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

    @Override
    public Person clone() {
        return new Person(this);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (!other.getClass().equals(this.getClass())) return false;
        else {
            Person otherPerson = (Person) other;
            return otherPerson.getID().equals(this.getID());
        }
    }

    @Override
    public int hashCode() {
        return getID().hashCode();
    }

    public static class Builder {
        private Person _person;

        public Builder(String firstname, String lastname) {
            _person = new Person();
            this.withFirstname(firstname);
            this.withLastname(lastname);
            this.withInsertionDate(new Date());
        }

        public Builder withFirstname(String firstname) {
            if (firstname == null || firstname.equals("")) {
                throw new IllegalArgumentException("first name cannot be null");
            }
            _person.setFirstname(firstname);
            return this;
        }

        public Builder withLastname(String lastname) {
            if (lastname == null || lastname.equals("")) {
                throw new IllegalArgumentException("first name cannot be null");
            }
            _person.setLastname(lastname);
            return this;
        }

        public Builder withID(String ID) {
            _person.setID(ID);
            return this;
        }

        public Builder withFacebookID(String facebookID) {
            _person.setFacebookID(facebookID);
            return this;
        }

        public Builder withPhoneNo(String phoneNo) {
            _person.setPhoneNo(phoneNo);
            return this;
        }

        public Builder withInsertionDate(Date insertionDate) {
            _person.setInsertionDate(insertionDate);
            return this;
        }

        public Person build() {
            return _person.clone();
        }
    }
}
