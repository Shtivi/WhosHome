package whosHome.common.sensors.commands;

public abstract class SensorCommand {
    public abstract String getName();

    @Override
    public String toString() {
        return this.getName();
    }
}