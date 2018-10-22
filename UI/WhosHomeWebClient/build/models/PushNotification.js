"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var NotificationType;
(function (NotificationType) {
    NotificationType["SENSOR_STATUS_CHANGED"] = "SENSOR_STATUS_CHANGED";
    NotificationType["SENSOR_ERROR"] = "SENSOR_ERROR";
    NotificationType["ENGINE_STATUS_CHANGED"] = "ENGINE_STATUS_CHANGED";
    NotificationType["ACTIVITY_DETECTION"] = "ACTIVITY_DETECTION";
})(NotificationType = exports.NotificationType || (exports.NotificationType = {}));
var PushNotification = (function () {
    function PushNotification(type, payload, receiveTime) {
        this._notificationType = type;
        this._payload = payload;
        this._readed = false;
        this._receiveTime = receiveTime || new Date();
    }
    PushNotification.prototype.receiveTime = function () {
        return this._receiveTime;
    };
    PushNotification.prototype.notificationType = function () {
        return this._notificationType;
    };
    PushNotification.prototype.payload = function () {
        return this._payload;
    };
    PushNotification.prototype.markAsReaded = function () {
        this._readed = true;
    };
    PushNotification.of = function (type, payload) {
        return new PushNotification(type, payload);
    };
    return PushNotification;
}());
exports.default = PushNotification;
//# sourceMappingURL=PushNotification.js.map