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
var styles_1 = require("@material-ui/core/styles");
var react_redux_1 = require("react-redux");
var styles = function (theme) { return styles_1.createStyles({}); };
var PresencePage = (function (_super) {
    __extends(PresencePage, _super);
    function PresencePage() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    return PresencePage;
}(React.Component));
var mapStateToProps = function (state) { return ({
    presenceState: state.presence
}); };
var mapDispatchToProps = function (dispatch) { return ({
    dispatch: dispatch
}); };
exports.default = styles_1.withStyles(styles)(react_redux_1.connect(mapStateToProps, mapDispatchToProps)(PresencePage));
//# sourceMappingURL=PresencePage.js.map