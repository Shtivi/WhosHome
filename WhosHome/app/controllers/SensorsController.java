package controllers;

import java.util.stream.Collectors;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.node.ObjectNode;

import bl.informationEngine.InformingManager;
import exceptions.InvalidSensorActionException;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class SensorsController extends Controller {
	// Data members
	private InformingManager engine;
	
	// Ctor
	@Inject
	public SensorsController(InformingManager engine) {
		this.engine = engine;
	}
	
	// API
		
	public Result getSensors() {
		return ok(Json.toJson(engine.getAttachedSensors()));
	}
	
	public Result toggleSensor(int sensorID) {
		try {
			engine.toggleSensor(sensorID);
			return ok();
		} catch (InvalidSensorActionException e) {
			return badRequest(e.getMessage());
		}
	}
	
}
