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
var AppBar_1 = require("@material-ui/core/AppBar");
var Toolbar_1 = require("@material-ui/core/Toolbar");
var styles_1 = require("@material-ui/core/styles");
var PushNotifications_1 = require("./PushNotifications");
var Avatar_1 = require("@material-ui/core/Avatar");
var styles = styles_1.createStyles({
    appBar: {
        background: 'transparent',
        boxShadow: 'none'
    },
    rightSide: {
        marginLeft: 'auto'
    },
    avatar: {
        border: '1px solid #fff'
    }
});
var StatusBar = (function (_super) {
    __extends(StatusBar, _super);
    function StatusBar() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    StatusBar.prototype.render = function () {
        var classes = this.props.classes;
        return (React.createElement(AppBar_1.default, { className: classes.appBar, position: "sticky" },
            React.createElement(Toolbar_1.default, null,
                React.createElement(Avatar_1.default, { className: classes.avatar }, "IS"),
                React.createElement("div", { className: classes.rightSide },
                    React.createElement(PushNotifications_1.default, null)))));
    };
    return StatusBar;
}(React.Component));
exports.default = styles_1.withStyles(styles)(StatusBar);
//# sourceMappingURL=StatusBar.js.map