package whosHome.whosHomeApp.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import whosHome.common.sensors.client.events.ErrorEventArgs;
import whosHome.common.sensors.client.events.StatusChangeEventArgs;
import whosHome.whosHomeApp.engine.WhosHomeEngine;
import whosHome.whosHomeApp.engine.events.EngineStatusChangedEventArgs;
import whosHome.whosHomeApp.engine.events.PersonActivityEventArgs;

@Component
public class NotificationsService {
    private final Logger _logger = Logger.getLogger(this.getClass().getName());
    private SimpMessagingTemplate _messagingTemplate;
    private WhosHomeEngine _engine;

    @Autowired
    public NotificationsService(WhosHomeEngine engine, SimpMessagingTemplate messagingTemplate) {
        _messagingTemplate = messagingTemplate;
        setEngine(engine);
    }

    @EventListener
    public void handleWebsocketConnection(SessionConnectEvent connectionEvent) {
        _logger.info("websocket connection");
    }

    @EventListener
    public void handleWebsocketDisconnection(SessionDisconnectEvent disconnectionEvent) {
        _logger.info("webosocket disconnection");
    }

    private void setEngine(WhosHomeEngine whosHomeEngine) {
        if (_engine != null) {
            _engine.onSensorStatusChanged().removeListener(this::notifySensorStatusChange);
            _engine.onSensorError().removeListener(this::notifySensorError);
            _engine.onEngineStatusChanged().removeListener(this::notifyEngineStatusChange);
            _engine.onActivityDetection().removeListener(this::notifyActivityDetected);
        }

        _engine = whosHomeEngine;
        _engine.onSensorStatusChanged().listen(this::notifySensorStatusChange);
        _engine.onActivityDetection().listen(this::notifyActivityDetected);
        _engine.onEngineStatusChanged().listen(this::notifyEngineStatusChange);
        _engine.onSensorError().listen(this::notifySensorError);
    }

    private void notifySensorStatusChange(StatusChangeEventArgs eventArgs) {
        _messagingTemplate.convertAndSend("/topics/sensors/status", eventArgs);
    }

    private void notifySensorError(ErrorEventArgs eventArgs) {
        _messagingTemplate.convertAndSend("/topics/sensors/error", eventArgs);
    }

    private void notifyEngineStatusChange(EngineStatusChangedEventArgs eventArgs) {
        _messagingTemplate.convertAndSend("/topics/engine/status", eventArgs);
    }

    private void notifyActivityDetected(PersonActivityEventArgs eventArgs) {
        _messagingTemplate.convertAndSend("/topics/people/detection", eventArgs);
    }
}
