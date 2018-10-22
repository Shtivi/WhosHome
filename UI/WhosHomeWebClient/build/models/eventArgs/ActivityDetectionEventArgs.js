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
var ActivityType;
(function (ActivityType) {
    ActivityType["IN"] = "IN";
    ActivityType["OUT"] = "OUT";
})(ActivityType = exports.ActivityType || (exports.ActivityType = {}));
var ActivityDetectionEventArgs = (function (_super) {
    __extends(ActivityDetectionEventArgs, _super);
    function ActivityDetectionEventArgs() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    return ActivityDetectionEventArgs;
}(AbstractEventArgs_1.default));
exports.default = ActivityDetectionEventArgs;
//# sourceMappingURL=ActivityDetectionEventArgs.js.map