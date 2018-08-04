package whosHome.common.sensors.client.commands;

public abstract class SensorCommand {
    public abstract String getName();

    @Override
    public String toString() {
        return this.getName();
    }
}