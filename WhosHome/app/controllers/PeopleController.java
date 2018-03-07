package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import bl.dataAccessors.PeopleAccessor;
import bl.dataAccessors.PeopleAccessor.SearchParams;
import models.Person;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class PeopleController extends Controller {
	
	public Result search() throws Exception {
		// Create search params & search
		//SearchParams params = Json.fromJson(request().body().asJson(), SearchParams.class);
		SearchParams params = PeopleAccessor.instance().createSearchParams()
				.setMacAddress("94-e9-79-67-68-53");
		Person[] results = PeopleAccessor.instance().search(params);
		
		return ok(Json.toJson(results));
	}
}