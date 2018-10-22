"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var ActionTypes;
(function (ActionTypes) {
    ActionTypes["SUBSCRIBE_PUSH"] = "SUBSCRIBE_PUSH";
    ActionTypes["UNSUBSCRIBE_PUSH"] = "UNSUBSCRIBE_PUSH";
    ActionTypes["SUBSCRIBING_PUSH"] = "SUBSCRIBING_PUSH";
    ActionTypes["PUSH_SUBSCRIBED_SUCCESSFULLY"] = "PUSH_SUBSCRIBED_SUCCESSFULLY";
    ActionTypes["PUSH_SUBSCRIPTION_ERROR"] = "PUSH_SUBSCRIPTION_ERROR";
    ActionTypes["PUSH_DISCONNECTED"] = "PUSH_DISCONNECTED";
    ActionTypes["PUSH_RECEIVED"] = "PUSH_RECEIVED";
    ActionTypes["TOGGLE_SENSOR"] = "TOGGLE_SENSOR";
    ActionTypes["FETCH_SENSORS"] = "FETCH_SENSORS";
    ActionTypes["FETCH_PRESENCE_DATA"] = "FETCH_PRESENCE_DATA";
})(ActionTypes = exports.ActionTypes || (exports.ActionTypes = {}));
var AsyncStatus;
(function (AsyncStatus) {
    AsyncStatus[AsyncStatus["PENDING"] = 0] = "PENDING";
    AsyncStatus[AsyncStatus["COMPLETED"] = 1] = "COMPLETED";
    AsyncStatus[AsyncStatus["ERROR"] = 2] = "ERROR";
})(AsyncStatus = exports.AsyncStatus || (exports.AsyncStatus = {}));
//# sourceMappingURL=ActionTypes.js.map