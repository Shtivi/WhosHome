package whosHome.common.sensors.client.commands;

import whosHome.common.exceptions.WhosHomeException;

import java.util.Optional;

public class SensorCommand<T> {
    private T _arg;
    private String _name;

    public SensorCommand(String name, T arg) {
        if (name == null || name.length() == 0) {
            throw new WhosHomeException("command name is required");
        }

        this._name = name;
        this._arg = arg;
    }

    public SensorCommand(String name) {
        this(name, null);
    }

    public static <T> SensorCommand<T> of(String name) {
        return new SensorCommand<>(name);
    }

    public static <T> SensorCommand<T> of(String name, T arg) {
        return new SensorCommand<>(name, arg);
    }

    public String name() {
        return _name;
    }

    public Optional<T> arg() {
        return Optional.of(_arg);
    }

    public SensorCommand<T> withArg(T arg) {
        this._arg = arg;
        return this;
    }

    @Override
    public String toString() {
        return this.name();
    }
}