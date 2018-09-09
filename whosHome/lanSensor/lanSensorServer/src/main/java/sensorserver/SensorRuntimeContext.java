package sensorserver;

import com.typesafe.config.Config;
import org.apache.commons.cli.CommandLine;
import sensorserver.engine.Engine;
import sensorserver.engine.EngineStatus;
import sensorserver.server.ISensorService;

public class SensorRuntimeContext {
    public enum Environment {DEBUG, PROD}

    private Config _config;
    private Engine _engine;
    private ISensorService _server;
    private Environment _Environment;
    private CommandLine _cmd;
    private boolean _simulationMode;

    public SensorRuntimeContext(Config config, Engine engine, ISensorService service, Environment environment, boolean simulationMode, CommandLine cmd) {
        this.setConfig(config);
        this.setEngine(engine);
        this.setServer(service);
        this._Environment = environment;
        this._cmd = cmd;
        this._simulationMode = simulationMode;
    }

    public Config getConfig() {
        return this._config;
    }

    public Engine getEngine() {
        return this._engine;
    }

    public CommandLine getCommandLine() {
        return this._cmd;
    }

    public ISensorService getSensorService() {
        return this._server;
    }

    public void start() {
        this._engine.start();
        this._server.start();
    }

    public boolean simulationMode () {
        return _simulationMode;
    }

    public void shutdown() throws Exception {
        this._server.stop();

        if (this._engine.getStatus() == EngineStatus.RUNNING) {
            this._engine.stop();
        }
    }

    public Environment getRunEnvironment() {
        return _Environment;
    }

    private void setConfig(Config config) {
        if (config == null) {
            throw new IllegalArgumentException("config is null");
        }

        this._config = config;
    }

    private void setEngine(Engine engine) {
        if (engine == null) {
            throw new IllegalArgumentException("engine is null");
        }

        this._engine = engine;
    }

    private void setServer(ISensorService server) {
        if (server == null) {
            throw new IllegalArgumentException("sensor service is null");
        }

        this._server = server;
    }
}
