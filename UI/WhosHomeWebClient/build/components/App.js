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
require("../../css/app.css");
require("../../fonts/fonts.css");
var StatusBar_1 = require("./StatusBar");
var react_router_dom_1 = require("react-router-dom");
var NavMenu_1 = require("./NavMenu");
var WhosHomeMuiTheme_1 = require("../config/WhosHomeMuiTheme");
var styles_1 = require("@material-ui/core/styles");
var SensorsPage_1 = require("./SensorsPage");
var styles_2 = require("@material-ui/core/styles");
var react_redux_1 = require("react-redux");
var PushNotificationsActionCreators_1 = require("../actions/PushNotificationsActionCreators");
var styles = function (theme) { return styles_2.createStyles({
    root: {
        backgroundImage: "url('/pics/1.png')",
        backgroundRepeat: 'no-repeat',
        backgroundSize: '100%',
        height: '100%'
    }
}); };
var App = (function (_super) {
    __extends(App, _super);
    function App(props) {
        var _this = _super.call(this, props) || this;
        _this.props.dispatch(PushNotificationsActionCreators_1.subscribePushNotifications());
        return _this;
    }
    App.prototype.render = function () {
        var classes = this.props.classes;
        return (React.createElement(styles_1.MuiThemeProvider, { theme: WhosHomeMuiTheme_1.default },
            React.createElement("div", { className: classes.root },
                React.createElement(StatusBar_1.default, null),
                React.createElement(react_router_dom_1.Route, { exact: true, path: '/sensors', component: function () { return React.createElement(SensorsPage_1.default, null); } }),
                React.createElement(NavMenu_1.default, null))));
    };
    return App;
}(React.Component));
var mapDispatchToProps = function (dispatch) { return ({
    dispatch: dispatch
}); };
exports.default = styles_2.withStyles(styles)(react_router_dom_1.withRouter(react_redux_1.connect(null, mapDispatchToProps)(App)));
//# sourceMappingURL=App.js.map