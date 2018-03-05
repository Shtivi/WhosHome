package bl.sensors;

import bl.informationEngine.Hub;

public interface ISensor {
	/**
	 * Starts the sensor activity
	 */
	public void start() throws Exception;
	
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
	
	/**
	 * Returns the hub the sensor is attached to
	 */
	public Hub<SensorEventData> getHub();
	
	/**
	 * Receives a new hub to attach the sensor to
	 */
	public void setHub(Hub<SensorEventData> hub);
	
	/**
	 * Detaches the hub from the sensor and stops it
	 */
	public void detachHub();
	
	
	/**
	 * Returns true if the sensor is ready for action
	 */
	public boolean ready();
}
