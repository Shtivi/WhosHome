"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var ActionTypes_1 = require("./ActionTypes");
exports.subscribePushNotifications = function () { return ({
    type: ActionTypes_1.ActionTypes.SUBSCRIBE_PUSH
}); };
exports.unsubsribePushNotifications = function () { return ({
    type: ActionTypes_1.ActionTypes.UNSUBSCRIBE_PUSH
}); };
exports.pushNotificationsSubscribed = function () { return ({
    type: ActionTypes_1.ActionTypes.PUSH_SUBSCRIBED_SUCCESSFULLY
}); };
exports.subscribingPush = function () { return ({
    type: ActionTypes_1.ActionTypes.SUBSCRIBING_PUSH
}); };
exports.pushSubscriptionError = function (error) { return ({
    type: ActionTypes_1.ActionTypes.PUSH_SUBSCRIPTION_ERROR,
    payload: error
}); };
exports.pushDisconnected = function (reason) { return ({
    type: ActionTypes_1.ActionTypes.PUSH_DISCONNECTED,
    payload: reason
}); };
exports.pushReceived = function (pushNotification) { return ({
    type: ActionTypes_1.ActionTypes.PUSH_RECEIVED,
    payload: pushNotification
}); };
//# sourceMappingURL=PushNotificationsActionCreators.js.map