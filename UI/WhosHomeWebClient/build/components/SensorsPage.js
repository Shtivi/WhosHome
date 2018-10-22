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
var Button_1 = require("@material-ui/core/Button");
var CircularProgress_1 = require("@material-ui/core/CircularProgress");
var List_1 = require("@material-ui/core/List");
var SensorActionCreators_1 = require("../actions/SensorActionCreators");
var SensorListItem_1 = require("./SensorListItem");
var SensorStatusChanged_1 = require("../models/eventArgs/SensorStatusChanged");
var styles = function (theme) { return styles_1.createStyles({
    root: {
        marginTop: '75px',
        textAlign: 'center',
    },
    statsWrapper: {
        position: 'relative',
    },
    statsContent: {
        backgroundColor: '#fff',
        height: '100px',
        width: '100px',
        boxShadow: 'none',
        color: '#555',
        fontSize: '42px',
        fontWeight: 'bold',
    },
    statsProgs: {
        position: 'absolute',
        top: '50%',
        left: '50%',
        marginTop: -50,
        marginLeft: -50,
    },
    sensorsList: {
        marginTop: '5%',
        paddingBottom: '6%'
    }
}); };
var mapStateToProps = function (state) { return ({
    sensorsState: state.sensors
}); };
var mapDispatchToProps = function (dispatch) { return ({
    dispatch: dispatch
}); };
var SensorsPage = (function (_super) {
    __extends(SensorsPage, _super);
    function SensorsPage(props) {
        var _this = _super.call(this, props) || this;
        _this.handleSensorToggle = function (sensorID) {
            _this.props.dispatch(SensorActionCreators_1.toggleSensor(sensorID));
        };
        _this.countActiveSensors = function () {
            var sensors = _this.props.sensorsState.sensors;
            return Object.keys(sensors)
                .filter(function (sensorID) { return sensors[Number(sensorID)].status == SensorStatusChanged_1.SensorConnectionState.CONNECTED; })
                .length;
        };
        _this.props.dispatch(SensorActionCreators_1.fetchAllSensors());
        return _this;
    }
    SensorsPage.prototype.render = function () {
        var _this = this;
        var classes = this.props.classes;
        var sensorsState = this.props.sensorsState;
        var activeSensorsCount = this.countActiveSensors();
        var allSensorsCount = Object.keys(sensorsState.sensors).length;
        return (React.createElement("div", { className: classes.root },
            React.createElement("div", { className: classes.statsWrapper },
                React.createElement(Button_1.default, { variant: "fab", className: classes.statsContent },
                    activeSensorsCount,
                    React.createElement("span", { style: { fontSize: '16px' } },
                        "/",
                        allSensorsCount)),
                React.createElement(CircularProgress_1.default, { variant: "static", value: (activeSensorsCount * 100) / allSensorsCount, size: 100, className: classes.statsProgs })),
            React.createElement(List_1.default, { className: classes.sensorsList }, Object.keys(sensorsState.sensors).map(function (sensorID) { return (React.createElement(SensorListItem_1.default, { key: sensorID, sensorConnection: sensorsState.sensors[Number(sensorID)], sensorToggleHandler: _this.handleSensorToggle })); }))));
    };
    return SensorsPage;
}(React.Component));
exports.default = styles_1.withStyles(styles)(react_redux_1.connect(mapStateToProps, mapDispatchToProps)(SensorsPage));
//# sourceMappingURL=SensorsPage.js.map