package whosHome.whosHomeApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import whosHome.common.sensors.client.ISensorConnection;
import whosHome.whosHomeApp.engine.WhosHomeEngine;

@RestController
@RequestMapping(value = "/sensors")
public class SensorsController {
    private WhosHomeEngine _whosHomeEngine;

    @Autowired
    public SensorsController(WhosHomeEngine whosHomeEngine) {
        _whosHomeEngine = whosHomeEngine;
    }

    @GetMapping
    public Iterable<ISensorConnection> getAllConnections() {
        return _whosHomeEngine.getAllSensorConnections();
    }
}
