"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var redux_1 = require("redux");
var RootReducer_1 = require("../reducers/RootReducer");
var PushNotificationsSubscriber_1 = require("../middleware/PushNotificationsSubscriber");
var PromiseResolver_1 = require("../middleware/PromiseResolver");
exports.store = redux_1.createStore(RootReducer_1.default, redux_1.applyMiddleware(PushNotificationsSubscriber_1.default, PromiseResolver_1.default));
//# sourceMappingURL=Store.js.map