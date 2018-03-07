package bl.dataAccessors;

import java.util.List;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import infra.Global;
import jdk.net.SocketFlow.Status;
import models.Person;
import utils.GsonParser;

public class PeopleAccessor {
	// Singleton
	private static PeopleAccessor _instance;
	
	public static PeopleAccessor instance() {
		if (_instance == null) {
			_instance = new PeopleAccessor();
		}
		
		return _instance;
	}
	
	// Ctor
	private PeopleAccessor() {
		
	}
	
	// API
	
	public Person getByID(String _id) throws Exception {
		// Execute a get request and fetch the person as json
		JsonNode personJson = 
				Unirest.get(Global.PEOPLE_SERVICE_URL + "/api/people/" + _id)
					.asJson()
					.getBody();
		
		// Convert to Person instance
		Person result = GsonParser.instance().fromJson(personJson.toString(), Person.class);
		
		return (result);
	}
	
	public Person[] search(SearchParams params) throws Exception {
		// Execute query and receive results as json
		HttpResponse<JsonNode> response = 
				Unirest.post(Global.PEOPLE_SERVICE_URL + "/api/people/search")
				.header("accept", "application/json")
		        .header("Content-Type", "application/json")
				.body(GsonParser.instance().toJson(params))
				.asJson();
		
		// Check if request succeed
		if (response.getStatus() != 200) {
			throw new Exception("Error accessing the people service API: " + response.getStatusText());
		} else {
			// Convert to list of people and return
			Person[] results = GsonParser.instance().fromJson(response.getBody().toString(), Person[].class);
		
			return (results);
		}
	}
	
	public SearchParams createSearchParams() {
		return (new SearchParams());
	}
	
	// Search params inner class
	public static class SearchParams {
		// Data members
		private String ID;
		private String firstname;
		private String lastname;
		private String macAddress;
		private String facebookID;
		private String phoneNo;
		
		// Ctor
		private SearchParams() {}
		
		// Setters
		
		public SearchParams setID(String ID) {
			this.ID = ID;
			return this;
		}
		
		public SearchParams setFirstname(String firstname) {
			this.firstname = firstname;
			return this;
		}
		
		public SearchParams setLastname(String lastname) {
			this.lastname = lastname;
			return this;
		}
		
		public SearchParams setMacAddress(String macAddress) {
			this.macAddress = macAddress;
			return this;
		}
		
		public SearchParams setFacebookID(String fbID) {
			this.facebookID = fbID;
			return this;
		}
		
		public SearchParams setPhoneNo(String phoneNo) {
			this.phoneNo = phoneNo;
			return this;
		}
	}
}
