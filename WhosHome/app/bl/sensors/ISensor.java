package bl.sensors;

import bl.informationEngine.Hub;

public interface ISensor {
	/**
	 * Starts the sensor activity
	 */
	public void start();
	
	/**
	 * Stops the sensor activity
	 */
	public void stop();
	
	/**
	 * Returns the sensor serial number 
	 */
	public int getID();
	
	/**
	 * Returns the sensor type (as SensorType enum)
	 */
	public SensorType getSensorType();
	
	public Hub<SensorEventData> getHub();
	
	public void setHub(Hub<SensorEventData> hub);
	
	public void detachHub();
}
