"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var es6_promise_1 = require("es6-promise");
var axios_1 = require("axios");
var config_1 = require("../config/config");
var SensorAgent = (function () {
    function SensorAgent(sensorsApiConfig) {
        this.sensorsApiConfig = sensorsApiConfig;
    }
    SensorAgent.prototype.fetchAllSensors = function () {
        var _this = this;
        return new es6_promise_1.Promise(function (resolve, reject) {
            axios_1.default.get(_this.sensorsApiConfig.endpoint + _this.sensorsApiConfig.api.fetchAllSensors)
                .then(function (res) { return resolve(res.data); })
                .catch(reject);
        });
    };
    SensorAgent.prototype.toggleSensor = function (sensorID) {
        var _this = this;
        return new es6_promise_1.Promise(function (resolve, reject) {
            axios_1.default.get(_this.sensorsApiConfig.endpoint + "/" + _this.sensorsApiConfig.api.toggleSensor + "/" + sensorID)
                .then(function (res) { return resolve(); })
                .catch(function (error) { return reject(error); });
        });
    };
    return SensorAgent;
}());
exports.SensorAgent = SensorAgent;
exports.default = new SensorAgent(config_1.default.sensors);
//# sourceMappingURL=SensorsAgent.js.map