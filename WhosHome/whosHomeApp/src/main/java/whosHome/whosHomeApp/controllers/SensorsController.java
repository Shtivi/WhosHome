package whosHome.whosHomeApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseErrorHandler;
import whosHome.common.sensors.client.ISensorConnection;
import whosHome.whosHomeApp.engine.WhosHomeEngine;
import whosHome.whosHomeApp.engine.errors.WhosHomeEngineException;

@RestController
@RequestMapping(value = "/sensors")
public class SensorsController {
    private WhosHomeEngine _whosHomeEngine;

    @Autowired
    public SensorsController(WhosHomeEngine whosHomeEngine) {
        _whosHomeEngine = whosHomeEngine;
    }

    @GetMapping
    public ResponseEntity<Iterable<ISensorConnection>> getAllConnections() {
        Iterable<ISensorConnection> connections = _whosHomeEngine.getAllSensorConnections();
        return ResponseEntity.ok(connections);
    }
}
