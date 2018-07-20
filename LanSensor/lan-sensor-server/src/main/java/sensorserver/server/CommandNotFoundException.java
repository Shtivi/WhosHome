package sensorserver.server;

import sensorserver.exceptions.SensorException;

public class CommandNotFoundException extends SensorException {
    public CommandNotFoundException(String commandName) {
        super(String.format("unkown command: '%s'", commandName));
    }
}
