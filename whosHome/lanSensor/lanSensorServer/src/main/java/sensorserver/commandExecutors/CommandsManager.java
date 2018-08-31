package sensorserver.commandExecutors;

import sensorserver.SensorRuntimeContext;
import sensorserver.server.CommandNotFoundException;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandsManager {
    private SensorRuntimeContext _context;
    private Map<String, ICommandExecutor> _commands;

    public CommandsManager(SensorRuntimeContext context) {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }

        this._context = context;
        this._commands = new ConcurrentHashMap<>();
        this.buildCommandsMap();
    }

    public SensorRuntimeContext getContext() {
        return this._context;
    }

    public void executeCommand(String commandName, InetSocketAddress requester) {
        if (_commands.containsKey(commandName)) {
            _commands.get(commandName).execute(_context, requester);;
        } else {
            throw new CommandNotFoundException(commandName);
        }
    }

    public boolean addCommandExecutor(String commandName, ICommandExecutor executor) {
        if (_commands.containsKey(commandName)) {
            return false;
        } else {
            _commands.put(commandName, executor);
            return true;
        }
    }

    public boolean removeCommandExecutor(String commandName) {
        return _commands.remove(commandName) != null;
    }

    private void buildCommandsMap() {
        this.addCommandExecutor("shutdownEngine", (context, requester) -> context.getEngine().stop());

        this.addCommandExecutor("startEngine", (context, requester) -> context.getEngine().start());

        this.addCommandExecutor("allEntities", (context, requester) -> {
                context.getSensorService().broadcastEvent(requester,
                        "allEntities",
                        context.getEngine().getEntitiesHolder().getPresentEntities());
        });
    }
}
