"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var ActionTypes_1 = require("./ActionTypes");
var SensorsAgent_1 = require("../services/SensorsAgent");
var DeferredAction_1 = require("./DeferredAction");
exports.toggleSensor = function (sensorID) {
    return DeferredAction_1.DeferredActionBuilder
        .of(ActionTypes_1.ActionTypes.TOGGLE_SENSOR)
        .withExtraArg(sensorID)
        .withPromise(SensorsAgent_1.default.toggleSensor(sensorID))
        .build();
};
exports.fetchAllSensors = function () {
    return DeferredAction_1.DeferredActionBuilder
        .of(ActionTypes_1.ActionTypes.FETCH_SENSORS)
        .withPromise(SensorsAgent_1.default.fetchAllSensors())
        .build();
};
//# sourceMappingURL=SensorActionCreators.js.map