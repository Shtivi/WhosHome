package infra;

import java.io.IOException;

import bl.informationEngine.InformingManager;
import controllers.NotificationsService;
import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {
	private NotificationsService notificationsService; 
	
	@Override
	public void onStart(Application app) {
		System.out.println("Starting notification websocket service...");
		notificationsService = new NotificationsService(app.injector().instanceOf(InformingManager.class), 5001);
		notificationsService.start();
	}
	
	@Override
	public void onStop(Application app) {
		// Stop notification service
		System.out.println("Stopping notification websocket service");
		try {
			notificationsService.stop();
		} catch (IOException | InterruptedException e) {
			System.out.println("Notifications service wasnt closed properly");
			e.printStackTrace();
		}
		
		// Stop sensors
		System.out.println("Shutting down sensors");
		app.injector().instanceOf(InformingManager.class).shutSensorsDown();
	}
}
