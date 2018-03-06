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
		// Build json
		ObjectNode json = Json.newObject();
		json.put("identified", Json.toJson(engine.getPresenceHolder().getIdentifiedEntities()));
		json.put("unknown", Json.toJson(engine.getPresenceHolder().getUnknownEntities()));
		
		return ok(json);
	}
}
