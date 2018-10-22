"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var redux_1 = require("redux");
var PushNotificationsReducer_1 = require("./PushNotificationsReducer");
var SensorsReducer_1 = require("./SensorsReducer");
var PresenceReducer_1 = require("./PresenceReducer");
var rootReducer = redux_1.combineReducers({
    push: PushNotificationsReducer_1.default,
    sensors: SensorsReducer_1.default,
    presence: PresenceReducer_1.default
});
exports.default = rootReducer;
//# sourceMappingURL=RootReducer.js.map