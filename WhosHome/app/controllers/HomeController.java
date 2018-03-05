package controllers;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import bl.identifiers.IdentificationData;
import bl.informationEngine.InformingManager;
import play.libs.Json;
import play.mvc.*;

public class HomeController extends Controller {
	// Data members
	private InformingManager engine;
	
	// Ctor
	@Inject
	public HomeController(InformingManager engine) {
		this.engine = engine;
	}
	
	// API
	
	public Result getPresentEntities() {
		ArrayNode result = Json.newArray();
		
		// Run over the present entities
		for (IdentificationData id : engine.getPresentEntities().keySet()) {
			// Convert the current entity to json object
			ObjectNode currentEntity = Json.newObject();
			currentEntity.put("identificationData", Json.toJson(id));
			currentEntity.put("person", Json.toJson(engine.getPresentEntities().get(id)));
			
			result.add(currentEntity);
		}
		
		return ok(result);
	}
}
