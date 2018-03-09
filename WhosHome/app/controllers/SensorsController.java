package controllers;

import java.util.stream.Collectors;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.node.ObjectNode;

import bl.informationEngine.InformingManager;
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
//		engine.getAttachedSensors().stream().map((sensor) -> {
//			ObjectNode sensorJson = Json.newObject();
//			sensorJson
//				.put("ID", sensor.getID())
//				.put("sensorType", sensor.getSensorType().toString())
//				.put("sensorName", sensor.getName())
//				.put("getSensorType", v)
//		}).collect(Collectors.toList());
		return ok(Json.toJson(engine.getAttachedSensors()));
	}
	
}
