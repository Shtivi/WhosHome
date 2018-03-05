package bl.sensors.lan;

import bl.informationEngine.Hub;
import bl.sensors.BaseSensor;
import bl.sensors.EventType;
import bl.sensors.SensorEventData;
import bl.sensors.SensorState;
import bl.sensors.SensorType;
import utils.GsonParser;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class LanSensor extends BaseSensor {
	private static final String SENSOR_URI = "ws://localhost:5010";  

	// Data members
	private WebSocketClient sensorWs;
	
	// Ctor
	public LanSensor(int ID, Hub<SensorEventData> hub) {
		super(ID, hub);
		
		try {
			// Create a web socket client to communicate with the sensor service
			sensorWs = new WebSocketClient(new URI(SENSOR_URI)) {
				
				@Override
				public void onOpen(ServerHandshake arg0) {
					setSensorState(SensorState.ACTIVE);
				}
				
				@Override
				public void onMessage(String msg) {
					// Parse the message to event and report it
					JsonObject json = GsonParser.instance().fromJson(msg, JsonObject.class);
					NetworkIdentificationData data = 
							new NetworkIdentificationData(json.get("ip").getAsString(), json.get("mac").getAsString());
					EventType eventType = EventType.valueOf(json.get("eventType").getAsString().toUpperCase());
					
					report(createEventData(eventType, data));
				}
				
				@Override
				public void onError(Exception arg0) {
					setSensorState(SensorState.ERROR);
				}
				
				@Override
				public void onClose(int arg0, String arg1, boolean arg2) {
					setSensorState(SensorState.READY);
				}
			};
		} catch (URISyntaxException e) {
			System.out.println("Error creating lan sensor client");
		}
	}
	
	// API

	@Override
	public void start() throws Exception {
		super.start();
		
		sensorWs.connect();
	}

	@Override
	public void stop() {
		sensorWs.close(0);
	}

	@Override
	public SensorType getSensorType() {
		return (SensorType.NETWORK);
	}

	@Override
	public String getName() {
		return ("Lan Scanner");
	}
}
