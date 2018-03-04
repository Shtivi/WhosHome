package controllers;

import javax.inject.Inject;

import bl.informationEngine.InformingManager;
import play.mvc.*;

import views.html.*;

public class HomeController extends Controller {
	// Ctor
	@Inject
	public HomeController(InformingManager engine) {
		System.out.println("Home controller created");
	}
	
	// API
	
	public Result blah() {
		return ok("123");
	}
}
