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
var EngineStatus;
(function (EngineStatus) {
    EngineStatus["CREATED"] = "Created";
    EngineStatus["INITIALIZED"] = "Initialized";
    EngineStatus["WORKING"] = "Working";
})(EngineStatus = exports.EngineStatus || (exports.EngineStatus = {}));
var EngineStatusChanged = (function (_super) {
    __extends(EngineStatusChanged, _super);
    function EngineStatusChanged() {
        return _super.call(this) || this;
    }
    return EngineStatusChanged;
}(AbstractEventArgs_1.default));
exports.default = EngineStatusChanged;
//# sourceMappingURL=EngineStatusChanged.js.map