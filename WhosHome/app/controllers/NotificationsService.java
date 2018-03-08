package controllers;

import java.net.InetSocketAddress;

import javax.inject.Inject;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import bl.informationEngine.ActivityEvent;
import bl.informationEngine.Hub;
import bl.informationEngine.InformingManager;
import bl.sensors.ISensor;
import bl.sensors.SensorState;
import bl.sensors.SensorStateChangedEvent;
import play.libs.Json;
import utils.GsonParser;

public class NotificationsService extends WebSocketServer implements Hub<ActivityEvent> {
	// Data members
	private InformingManager engine;
		
	// Ctor
	@Inject
	public NotificationsService(InformingManager engine, int PORT) {
		super(new InetSocketAddress(PORT));
		this.engine = engine;
		this.engine.registerObserver(this);
	}

	// API
	
	@Override
	public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
		
	}

	@Override
	public void onError(WebSocket arg0, Exception arg1) {
		
	}

	@Override
	public void onMessage(WebSocket arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpen(WebSocket arg0, ClientHandshake arg1) {
		
	}

	@Override
	public void onStart() {
		System.out.println("Notifications service on!");
	}

	@Override
	public void recieve(ActivityEvent eventData) {
		// Broadcast event to all sockets
		this.broadcast(Json.toJson(eventData).toString());
	}

	@Override
	public void sensorStateChanged(SensorStateChangedEvent event) {
		this.broadcast(Json.toJson(event).toString());
	}
}