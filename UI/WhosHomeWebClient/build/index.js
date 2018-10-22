"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var React = require("react");
var react_dom_1 = require("react-dom");
var react_redux_1 = require("react-redux");
var App_1 = require("./components/App");
var Store_1 = require("./store/Store");
var react_router_dom_1 = require("react-router-dom");
react_dom_1.render(React.createElement(react_redux_1.Provider, { store: Store_1.store },
    React.createElement(react_router_dom_1.BrowserRouter, null,
        React.createElement(App_1.default, null))), document.getElementById("root"));
//# sourceMappingURL=index.js.map