"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var ActionTypes_1 = require("../actions/ActionTypes");
var DeferredAction_1 = require("../actions/DeferredAction");
var PromiseResolver = function (store) { return function (next) { return function (action) {
    var dispatch = store.dispatch;
    if (action.promise && action.asyncStatus == ActionTypes_1.AsyncStatus.PENDING) {
        action.promise
            .then(function (data) { return dispatch(createSucessAction(action, data)); })
            .catch(function (error) { return dispatch(createFailureAction(action, error)); });
        ;
    }
    next(action);
}; }; };
var createSucessAction = function (originalAction, data) {
    return DeferredAction_1.DeferredActionBuilder
        .from(originalAction)
        .withAsyncStatus(ActionTypes_1.AsyncStatus.COMPLETED)
        .withPayload(data)
        .build();
};
var createFailureAction = function (originalAction, error) {
    return DeferredAction_1.DeferredActionBuilder
        .from(originalAction)
        .withAsyncStatus(ActionTypes_1.AsyncStatus.ERROR)
        .withError(error)
        .build();
};
exports.default = PromiseResolver;
//# sourceMappingURL=PromiseResolver.js.map