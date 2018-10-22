"use strict";
var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
Object.defineProperty(exports, "__esModule", { value: true });
var ActionTypes_1 = require("./ActionTypes");
var DeferredActionBuilder = (function () {
    function DeferredActionBuilder(type, asyncStatus) {
        this.action = {
            type: type,
            asyncStatus: asyncStatus | ActionTypes_1.AsyncStatus.PENDING
        };
    }
    DeferredActionBuilder.from = function (originalAction) {
        return new DeferredActionBuilder(originalAction.type);
    };
    DeferredActionBuilder.of = function (type) {
        return new DeferredActionBuilder(type);
    };
    DeferredActionBuilder.prototype.withPromise = function (promise) {
        this.action.promise = promise;
        return this;
    };
    DeferredActionBuilder.prototype.withPayload = function (payload) {
        this.action.payload = payload;
        return this;
    };
    DeferredActionBuilder.prototype.withError = function (error) {
        this.action.error = error;
        return this;
    };
    DeferredActionBuilder.prototype.withAsyncStatus = function (asyncStatus) {
        this.action.asyncStatus = asyncStatus;
        return this;
    };
    DeferredActionBuilder.prototype.withExtraArg = function (extra) {
        this.action.extra = extra;
        return this;
    };
    DeferredActionBuilder.prototype.build = function () {
        return __assign({}, this.action);
    };
    return DeferredActionBuilder;
}());
exports.DeferredActionBuilder = DeferredActionBuilder;
//# sourceMappingURL=DeferredAction.js.map