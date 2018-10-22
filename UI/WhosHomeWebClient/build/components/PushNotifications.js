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
var PushConnectionStatus_1 = require("../models/PushConnectionStatus");
var SvgIcon_1 = require("@material-ui/core/SvgIcon");
var Badge_1 = require("@material-ui/core/Badge");
var IconButton_1 = require("@material-ui/core/IconButton");
var CircularProgress_1 = require("@material-ui/core/CircularProgress");
var NotificationsOutlined_1 = require("@material-ui/icons/NotificationsOutlined");
var NotificationsOffOutlined_1 = require("@material-ui/icons/NotificationsOffOutlined");
var NotificationImportantOutlined_1 = require("@material-ui/icons/NotificationImportantOutlined");
var PushNotificationsActionCreators_1 = require("../actions/PushNotificationsActionCreators");
var react_redux_1 = require("react-redux");
var styles_1 = require("@material-ui/core/styles");
var Zoom_1 = require("@material-ui/core/Zoom");
var styles = function (theme) { return styles_1.createStyles({
    margin: {
        margin: theme.spacing.unit * 2,
    }
}); };
var PushNotifications = (function (_super) {
    __extends(PushNotifications, _super);
    function PushNotifications() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    PushNotifications.prototype.renderIcon = function () {
        var _this = this;
        var pushState = this.props.pushState;
        switch (pushState.connectionStatus) {
            case PushConnectionStatus_1.PushConnectionStatus.CONNECTED:
                return React.createElement(SvgIcon_1.default, { style: { fontSize: '32px' }, onClick: function () { return _this.props.dispatch(PushNotificationsActionCreators_1.unsubsribePushNotifications()); } },
                    React.createElement(NotificationsOutlined_1.default, null));
            case PushConnectionStatus_1.PushConnectionStatus.CONNECTING:
                return React.createElement(CircularProgress_1.default, null);
            case PushConnectionStatus_1.PushConnectionStatus.DISCONNECTED:
                return React.createElement(SvgIcon_1.default, { style: { fontSize: '32px' }, onClick: function () { return _this.props.dispatch(PushNotificationsActionCreators_1.subscribePushNotifications()); } },
                    React.createElement(NotificationsOffOutlined_1.default, null));
            case PushConnectionStatus_1.PushConnectionStatus.ERROR:
                return (React.createElement(SvgIcon_1.default, { style: { fontSize: '32px' }, color: 'error', onClick: function () { return _this.props.dispatch(PushNotificationsActionCreators_1.subscribePushNotifications()); } },
                    React.createElement(NotificationImportantOutlined_1.default, null)));
        }
    };
    PushNotifications.prototype.render = function () {
        var _a = this.props, classes = _a.classes, pushState = _a.pushState;
        var icon = this.renderIcon();
        return ((pushState.connectionStatus == PushConnectionStatus_1.PushConnectionStatus.CONNECTED && pushState.notificationsLog.length > 0) ?
            (React.createElement(IconButton_1.default, null,
                React.createElement(Zoom_1.default, { in: true },
                    React.createElement(Badge_1.default, { className: classes.margin, color: 'secondary', badgeContent: this.props.pushState.notificationsLog.length }, icon)))) :
            (React.createElement(IconButton_1.default, null, icon)));
    };
    return PushNotifications;
}(React.Component));
var mapStateToProps = function (state) { return ({
    pushState: state.push
}); };
var mapDispatchToProps = function (dispatch) { return ({
    dispatch: dispatch
}); };
exports.default = styles_1.withStyles(styles)(react_redux_1.connect(mapStateToProps, mapDispatchToProps)(PushNotifications));
//# sourceMappingURL=PushNotifications.js.map