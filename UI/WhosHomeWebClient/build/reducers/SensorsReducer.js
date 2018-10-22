"use strict";
var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
Object.defineProperty(exports, "__esModule", { value: true });
var ActionTypes_1 = require("../actions/ActionTypes");
var PushNotification_1 = require("../models/PushNotification");
var initialState = {
    sensors: {}
};
exports.default = (function (state, action) {
    if (state === void 0) { state = initialState; }
    switch (action.type) {
        case ActionTypes_1.ActionTypes.PUSH_RECEIVED:
            return handlePush(state, action.payload);
        case ActionTypes_1.ActionTypes.FETCH_SENSORS:
            var deferredAction = action;
            console.log(action);
            return handleSensorsFetching(state, deferredAction.payload, deferredAction.asyncStatus);
        default:
            return state;
    }
});
var handlePush = function (currentState, notification) {
    var updatedState = __assign({}, currentState);
    switch (notification.notificationType()) {
        case PushNotification_1.NotificationType.SENSOR_STATUS_CHANGED:
            var payload = notification.payload();
            var updatedState_1 = __assign({}, currentState);
            updatedState_1.sensors[payload.sensorConnectionMetadata.sensorConnectionID].status = payload.newStatus;
            return updatedState_1;
        case PushNotification_1.NotificationType.SENSOR_ERROR:
            console.warn(notification);
            break;
        default:
            break;
    }
    return updatedState;
};
var handleSensorsFetching = function (currentState, payload, status) {
    switch (status) {
        case ActionTypes_1.AsyncStatus.COMPLETED:
            var updatedState_2 = __assign({}, currentState);
            updatedState_2.sensors = {};
            payload.forEach(function (sensor) { return updatedState_2.sensors[sensor.connectionMetadata.sensorConnectionID] = sensor; });
            return updatedState_2;
        default:
            return currentState;
    }
};
//# sourceMappingURL=SensorsReducer.js.map