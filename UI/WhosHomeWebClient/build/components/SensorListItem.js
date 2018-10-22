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
var React = require("react");
var ListItem_1 = require("@material-ui/core/ListItem");
var ListItemIcon_1 = require("@material-ui/core/ListItemIcon");
var ListItemText_1 = require("@material-ui/core/ListItemText");
var ListItemSecondaryAction_1 = require("@material-ui/core/ListItemSecondaryAction");
var IconButton_1 = require("@material-ui/core/IconButton");
var SvgIcon_1 = require("@material-ui/core/SvgIcon");
var Power_1 = require("@material-ui/icons/Power");
var Wifi_1 = require("@material-ui/icons/Wifi");
var DeviceUnknown_1 = require("@material-ui/icons/DeviceUnknown");
var styles_1 = require("@material-ui/core/styles");
var blue_1 = require("@material-ui/core/colors/blue");
var green_1 = require("@material-ui/core/colors/green");
var red_1 = require("@material-ui/core/colors/red");
var SensorStatusChanged_1 = require("../models/eventArgs/SensorStatusChanged");
var core_1 = require("@material-ui/core");
var styles = function (theme) { return styles_1.createStyles({
    root: {},
    sensorTypeIcon: {
        fontSize: '40px'
    },
}); };
var SensorListItem = (function (_super) {
    __extends(SensorListItem, _super);
    function SensorListItem() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    SensorListItem.prototype.defineStatusColor = function () {
        switch (this.props.sensorConnection.status) {
            case SensorStatusChanged_1.SensorConnectionState.CONNECTED:
                return green_1.default[600];
            case SensorStatusChanged_1.SensorConnectionState.CONNECTING:
                return blue_1.default[600];
            case SensorStatusChanged_1.SensorConnectionState.INITIALIZED:
                return blue_1.default[600];
            case SensorStatusChanged_1.SensorConnectionState.CLOSED:
                return blue_1.default[600];
            case SensorStatusChanged_1.SensorConnectionState.ERROR:
                return red_1.default[600];
            default:
                return red_1.default[600];
        }
    };
    SensorListItem.prototype.renderSensorTypeIcon = function () {
        var sensorConnection = this.props.sensorConnection;
        var statusColor = this.defineStatusColor();
        switch (sensorConnection.connectionMetadata.sensorTypeMetadata.sensorTypeID) {
            case 1:
                return React.createElement(Wifi_1.default, { style: { color: statusColor } });
            default:
                return React.createElement(DeviceUnknown_1.default, { style: { color: statusColor } });
        }
    };
    SensorListItem.prototype.render = function () {
        var _this = this;
        var _a = this.props, sensorConnection = _a.sensorConnection, classes = _a.classes;
        return (React.createElement(ListItem_1.default, null,
            React.createElement(ListItemIcon_1.default, { className: classes.sensorTypeIcon }, this.renderSensorTypeIcon()),
            React.createElement(ListItemText_1.default, { primary: sensorConnection.connectionMetadata.name, secondary: sensorConnection.connectionMetadata.sensorTypeMetadata.title + ", " + sensorConnection.status }),
            React.createElement(ListItemSecondaryAction_1.default, null, (sensorConnection.status == SensorStatusChanged_1.SensorConnectionState.CONNECTING) ? (React.createElement(core_1.CircularProgress, null)) : (React.createElement(IconButton_1.default, { onClick: function () { return _this.props.sensorToggleHandler(sensorConnection.connectionMetadata.sensorConnectionID); } },
                React.createElement(SvgIcon_1.default, null,
                    React.createElement(Power_1.default, null)))))));
    };
    return SensorListItem;
}(React.Component));
exports.default = styles_1.withStyles(styles)(SensorListItem);
//# sourceMappingURL=SensorListItem.js.map