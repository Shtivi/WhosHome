"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var ActionTypes_1 = require("../actions/ActionTypes");
var SockJS = require("sockjs-client");
var Stomp = require("@stomp/stompjs");
var PushNotificationsActionCreators_1 = require("../actions/PushNotificationsActionCreators");
var PushNotification_1 = require("../models/PushNotification");
var config_1 = require("../config/config");
var client = null;
var dispatch = null;
var pushSubsription = function (store) { return function (next) { return function (action) {
    dispatch = store.dispatch;
    switch (action.type) {
        case ActionTypes_1.ActionTypes.SUBSCRIBE_PUSH:
            var socket = new SockJS(config_1.default.push.endpoint);
            client = Stomp.over(socket);
            store.dispatch(PushNotificationsActionCreators_1.subscribingPush());
            client.connect({}, handleConnection, handleError, handleDisconnection);
            break;
        case ActionTypes_1.ActionTypes.UNSUBSCRIBE_PUSH:
            if (client != null) {
                client.disconnect(handleDisconnection);
            }
            break;
    }
    next(action);
}; }; };
var handleConnection = function () {
    client.subscribe(config_1.default.push.topics.sensorsStatus, function (message) {
        var eventArgs = (JSON.parse(message.body));
        dispatch(PushNotificationsActionCreators_1.pushReceived(PushNotification_1.default.of(PushNotification_1.NotificationType.SENSOR_STATUS_CHANGED, eventArgs)));
    });
    client.subscribe(config_1.default.push.topics.sensorError, function (message) {
        var eventArgs = (JSON.parse(message.body));
        dispatch(PushNotificationsActionCreators_1.pushReceived(PushNotification_1.default.of(PushNotification_1.NotificationType.SENSOR_ERROR, eventArgs)));
    });
    client.subscribe(config_1.default.push.topics.engineStatus, function (message) {
        var eventArgs = (JSON.parse(message.body));
        dispatch(PushNotificationsActionCreators_1.pushReceived(PushNotification_1.default.of(PushNotification_1.NotificationType.ENGINE_STATUS_CHANGED, eventArgs)));
    });
    client.subscribe(config_1.default.push.topics.peopleDetection, function (message) {
        console.log(message.body);
    });
    dispatch(PushNotificationsActionCreators_1.pushNotificationsSubscribed());
};
var handleDisconnection = function (event) {
    dispatch(PushNotificationsActionCreators_1.pushDisconnected(event));
};
var handleError = function (error) {
    dispatch(PushNotificationsActionCreators_1.pushSubscriptionError(error));
};
exports.default = pushSubsription;
//# sourceMappingURL=PushNotificationsSubscriber.js.map