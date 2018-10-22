package whosHome.whosHomeApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import whosHome.whosHomeApp.engine.PersonPresenceData;
import whosHome.whosHomeApp.engine.WhosHomeEngine;

@RestController
@RequestMapping(value = "/api/presence")
public class PeoplePresenceController {
    private WhosHomeEngine _whosHomeEngine;

    @Autowired
    public PeoplePresenceController(WhosHomeEngine engine) {
        _whosHomeEngine = engine;
    }

    @GetMapping
    public ResponseEntity<Iterable<PersonPresenceData>> getAllPresenceData() {
        return ResponseEntity.ok(_whosHomeEngine.getPeoplePresenceData());
    }
}
