package whosHome.whosHomeApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whosHome.common.exceptions.WhosHomeException;
import whosHome.common.sensors.client.ISensorConnection;
import whosHome.common.sensors.client.SensorConnectionState;
import whosHome.whosHomeApp.engine.WhosHomeEngine;

@RestController
@RequestMapping(value = "/api/sensors")
public class SensorsController {
    private WhosHomeEngine _whosHomeEngine;

    @Autowired
    public SensorsController(WhosHomeEngine whosHomeEngine) {
        _whosHomeEngine = whosHomeEngine;
    }

    @GetMapping()
    public ResponseEntity<Iterable<ISensorConnection>> getAllConnections() {
        Iterable<ISensorConnection> connections = _whosHomeEngine.getAllSensorConnections();
        return ResponseEntity.ok(connections);
    }

    @GetMapping(path = "/toggle/{sensorID}")
    public ResponseEntity toggleSensor(@PathVariable(name = "sensorID") int sensorID) {
        ISensorConnection connection = _whosHomeEngine
                .getSensorConnection(sensorID)
                .orElseThrow(() -> new WhosHomeException(String.format("Could not find sensor with id '%d'", sensorID)));

        if (connection.getStatus() == SensorConnectionState.CONNECTED) {
            connection.disconnect();
        } else {
            connection.connect();
        }

        return ResponseEntity.ok().build();
    }
}
