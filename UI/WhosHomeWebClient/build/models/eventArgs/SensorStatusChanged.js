"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    }
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
var AbstractEventArgs_1 = require("./AbstractEventArgs");
var SensorConnectionState;
(function (SensorConnectionState) {
    SensorConnectionState["ERROR"] = "ERROR";
    SensorConnectionState["INITIALIZED"] = "INITIALIZED";
    SensorConnectionState["CLOSED"] = "CLOSED";
    SensorConnectionState["CONNECTING"] = "CONNECTING";
    SensorConnectionState["CONNECTED"] = "CONNECTED";
})(SensorConnectionState = exports.SensorConnectionState || (exports.SensorConnectionState = {}));
var SensorStatusChanged = (function (_super) {
    __extends(SensorStatusChanged, _super);
    function SensorStatusChanged() {
        return _super.call(this) || this;
    }
    return SensorStatusChanged;
}(AbstractEventArgs_1.default));
exports.default = SensorStatusChanged;
//# sourceMappingURL=SensorStatusChanged.js.map