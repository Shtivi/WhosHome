"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var SensorTypeMetadata = (function () {
    function SensorTypeMetadata(sensorTypeID, title, reliability) {
        this.sensorTypeID = sensorTypeID;
        this.title = title;
        this.reliability = reliability;
    }
    return SensorTypeMetadata;
}());
exports.SensorTypeMetadata = SensorTypeMetadata;
var SensorConnectionMetadata = (function () {
    function SensorConnectionMetadata(sensorConnectionID, url, port, path, name, isActiveDefaultly, sensorTypeMetadata) {
        this.sensorConnectionID = sensorConnectionID;
        this.url = url;
        this.port = port;
        this.path = path;
        this.name = name;
        this.isActiveDefaultly = isActiveDefaultly;
        this.sensorTypeMetadata = sensorTypeMetadata;
    }
    return SensorConnectionMetadata;
}());
exports.SensorConnectionMetadata = SensorConnectionMetadata;
var SensorConnection = (function () {
    function SensorConnection(connectionMetadata, status) {
        this.connectionMetadata = connectionMetadata;
        this.status = status;
    }
    return SensorConnection;
}());
exports.SensorConnection = SensorConnection;
//# sourceMappingURL=SensorConnection.js.map