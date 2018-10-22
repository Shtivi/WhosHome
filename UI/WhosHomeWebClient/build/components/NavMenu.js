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
var BottomNavigation_1 = require("@material-ui/core/BottomNavigation");
var BottomNavigationAction_1 = require("@material-ui/core/BottomNavigationAction");
var styles_1 = require("@material-ui/core/styles");
var RouterOutlined_1 = require("@material-ui/icons/RouterOutlined");
var PeopleOutlined_1 = require("@material-ui/icons/PeopleOutlined");
var SettingsOutlined_1 = require("@material-ui/icons/SettingsOutlined");
var NotificationsOutlined_1 = require("@material-ui/icons/NotificationsOutlined");
var NotificationsOffOutlined_1 = require("@material-ui/icons/NotificationsOffOutlined");
var NotificationImportantOutlined_1 = require("@material-ui/icons/NotificationImportantOutlined");
var react_router_dom_1 = require("react-router-dom");
var react_redux_1 = require("react-redux");
var PushConnectionStatus_1 = require("../models/PushConnectionStatus");
var core_1 = require("@material-ui/core");
var styles = function (theme) { return styles_1.createStyles({
    root: {
        borderTop: '1px solid #e9e9e9',
        position: 'fixed',
        bottom: '0px',
        width: '100%'
    },
    navIcon: {
        fontSize: '36px'
    },
    margin: {
        margin: theme.spacing.unit * 2,
    }
}); };
var NavMenu = (function (_super) {
    __extends(NavMenu, _super);
    function NavMenu(props) {
        var _this = _super.call(this, props) || this;
        _this.handleSelection = function (event, value) {
            _this.setState({
                value: value
            });
            _this.props.history.push(value);
        };
        _this.actions = [{
                label: '',
                icon: React.createElement(RouterOutlined_1.default, { className: _this.props.classes.navIcon }),
                value: '/sensors'
            }, {
                label: '',
                icon: React.createElement(PeopleOutlined_1.default, { className: _this.props.classes.navIcon }),
                value: '/'
            }, {
                label: '',
                icon: React.createElement(SettingsOutlined_1.default, { className: _this.props.classes.navIcon }),
                value: '/settings'
            }];
        _this.state = {
            value: props.location.pathname
        };
        return _this;
    }
    NavMenu.prototype.renderPushNotificationsIcon = function () {
        switch (this.props.pushState.connectionStatus) {
            case PushConnectionStatus_1.PushConnectionStatus.CONNECTED:
                return React.createElement(NotificationsOutlined_1.default, { className: this.props.classes.navIcon });
            case PushConnectionStatus_1.PushConnectionStatus.CONNECTING:
                return React.createElement(core_1.CircularProgress, null);
            case PushConnectionStatus_1.PushConnectionStatus.DISCONNECTED:
                return React.createElement(NotificationsOffOutlined_1.default, { className: this.props.classes.navIcon });
            case PushConnectionStatus_1.PushConnectionStatus.ERROR:
                return (React.createElement(NotificationImportantOutlined_1.default, { className: this.props.classes.navIcon }));
        }
    };
    NavMenu.prototype.render = function () {
        var classes = this.props.classes;
        return (React.createElement(BottomNavigation_1.default, { showLabels: false, className: classes.root, onChange: this.handleSelection, value: this.state.value }, this.actions.map(function (action, i) {
            return React.createElement(BottomNavigationAction_1.default, { key: i, label: action.label, icon: action.icon, value: action.value });
        })));
    };
    return NavMenu;
}(React.Component));
var mapStateToProps = function (state) { return ({
    pushState: state.push
}); };
var mapDispatchToProps = function (dispatch) { return ({
    dispatch: dispatch
}); };
exports.default = styles_1.withStyles(styles)(react_router_dom_1.withRouter(react_redux_1.connect(mapStateToProps, mapDispatchToProps)(NavMenu)));
//# sourceMappingURL=NavMenu.js.map