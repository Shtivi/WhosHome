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
var PushConnectionStatus_1 = require("../models/PushConnectionStatus");
var ActionTypes_1 = require("../actions/ActionTypes");
var initialState = {
    connectionStatus: PushConnectionStatus_1.PushConnectionStatus.DISCONNECTED,
    notificationsLog: []
};
exports.default = (function (state, action) {
    if (state === void 0) { state = initialState; }
    switch (action.type) {
        case ActionTypes_1.ActionTypes.SUBSCRIBING_PUSH:
            var updatedState = __assign({}, state);
            updatedState.connectionStatus = PushConnectionStatus_1.PushConnectionStatus.CONNECTING;
            updatedState.error = null;
            return updatedState;
        case ActionTypes_1.ActionTypes.PUSH_SUBSCRIBED_SUCCESSFULLY:
            var updatedState = __assign({}, state);
            updatedState.connectionStatus = PushConnectionStatus_1.PushConnectionStatus.CONNECTED;
            updatedState.error = null;
            return updatedState;
        case ActionTypes_1.ActionTypes.PUSH_SUBSCRIPTION_ERROR:
            var updatedState = __assign({}, state);
            updatedState.connectionStatus = PushConnectionStatus_1.PushConnectionStatus.ERROR;
            updatedState.error = action.payload;
            return updatedState;
        case ActionTypes_1.ActionTypes.PUSH_DISCONNECTED:
            var updatedState = __assign({}, state);
            updatedState.connectionStatus = PushConnectionStatus_1.PushConnectionStatus.DISCONNECTED;
            updatedState.error = null;
            return updatedState;
        case ActionTypes_1.ActionTypes.PUSH_RECEIVED:
            var updatedState = __assign({}, state);
            updatedState.notificationsLog.push(action.payload);
            return updatedState;
        default:
            return state;
    }
});
//# sourceMappingURL=PushNotificationsReducer.js.map