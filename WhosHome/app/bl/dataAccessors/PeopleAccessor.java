package bl.dataAccessors;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import infra.Global;
import models.Person;

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
		
		return null;
	}
}
