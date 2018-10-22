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
    recognized: []
};
exports.default = (function (state, action) {
    if (state === void 0) { state = initialState; }
    switch (action.type) {
        case ActionTypes_1.ActionTypes.PUSH_RECEIVED:
            return handlePush(state, action.payload);
        case ActionTypes_1.ActionTypes.FETCH_PRESENCE_DATA:
            var deferredAction = action;
            return handlePresenceDataFetching(state, deferredAction.payload, deferredAction.asyncStatus);
        default:
            return state;
    }
});
var handlePush = function (currentState, notification) {
    if (notification.notificationType() == PushNotification_1.NotificationType.ACTIVITY_DETECTION) {
        var updatedState = __assign({}, currentState);
        var payload = notification.payload();
        updatedState.recognized;
        return updatedState;
    }
    return currentState;
};
var handlePresenceDataFetching = function (currentState, payload, status) {
    switch (status) {
        case ActionTypes_1.AsyncStatus.COMPLETED:
            var updatedState = __assign({}, currentState);
            updatedState.recognized = payload;
            return updatedState;
        default:
            return currentState;
    }
};
//# sourceMappingURL=PresenceReducer.js.map