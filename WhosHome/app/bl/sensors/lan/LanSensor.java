package bl.sensors.lan;

import bl.informationEngine.Hub;
import bl.sensors.BaseSensor;
import bl.sensors.ISensor;
import bl.sensors.SensorEventData;
import bl.sensors.SensorType;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class LanSensor extends BaseSensor {
	private static final String SENSOR_URI = "ws://localhost:5010";  

	// Data members
	private SensorClient sensorWs;
	
	// Ctor
	public LanSensor(int ID, Hub<SensorEventData> hub) {
		super(ID, hub);
		
		try {
			this.sensorWs = new SensorClient(new URI(SENSOR_URI));
		} catch (URISyntaxException e) {
			System.out.println("Error creating lan sensor client");
		}
	}
	
	// API

	@Override
	public void start() {
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
	
	// Web socket client inner class
	public class SensorClient extends WebSocketClient {

		public SensorClient(URI serverUri) {
			super(serverUri);
		}

		@Override
		public void onClose(int arg0, String arg1, boolean arg2) {
		}

		@Override
		public void onError(Exception arg0) {
		}

		@Override
		public void onMessage(String arg0) {
			System.out.println(arg0);
		}

		@Override
		public void onOpen(ServerHandshake arg0) {
			System.out.println("Connected to lan sensor");
		}
	}
}
