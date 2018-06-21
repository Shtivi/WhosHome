package sensorserver.commandExecutors;

import sensorserver.SensorRuntimeContext;

import java.net.InetSocketAddress;

public interface ICommandExecutor {
    void execute(SensorRuntimeContext context, InetSocketAddress requester);
}
