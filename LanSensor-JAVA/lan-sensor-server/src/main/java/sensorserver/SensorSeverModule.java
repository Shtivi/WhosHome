package sensorserver;

import com.google.inject.AbstractModule;
import com.typesafe.config.Config;

public class SensorSeverModule extends AbstractModule {
    private Config _config;
    private SensorRuntimeContext.Environment _environment;
    private String _operationSystem;

    public SensorSeverModule(Config config, SensorRuntimeContext.Environment environment, String opeartionSystem) {
        _config = config;
    }

    @Override
    protected void configure() {
        if (_environment == SensorRuntimeContext.Environment.DEBUG) {
            initializeDebug();
        } else if (_environment == SensorRuntimeContext.Environment.PROD) {
            initializeProd();
        }
    }

    private void initializeDebug() {

    }

    private void initializeProd() {

    }
}
