import { ActionTypes, AsyncStatus } from "./ActionTypes";
import { Action } from "redux";
import { Promise } from "es6-promise";

export interface DeferredAction<R, E = any> extends Action<ActionTypes> {
    promise?: Promise<R>,
    asyncStatus: AsyncStatus,
    payload?: R,
    error?: Error,
    extra?: E
}

export class DeferredActionBuilder<T, E = any> {
    private action: DeferredAction<T, E>;

    public static from <T, E = any>(originalAction: Action<ActionTypes>): DeferredActionBuilder<T, E> {
        return new DeferredActionBuilder<T>(originalAction.type);
    }

    public static of <T, E = any>(type: ActionTypes): DeferredActionBuilder<T, E> {
        return new DeferredActionBuilder<T>(type);
    }

    public constructor(type: ActionTypes, asyncStatus?: AsyncStatus) {
        this.action = {
            type: type,
            asyncStatus: asyncStatus | AsyncStatus.PENDING
        }
    }

    public withPromise(promise: Promise<T>): DeferredActionBuilder<T, E> {
        this.action.promise = promise;
        return this;
    }

    public withPayload(payload: T): DeferredActionBuilder<T, E> {
        this.action.payload = payload;
        return this;
    }

    public withError(error: Error): DeferredActionBuilder<T, E> {
        this.action.error = error;
        return this;
    }

    public withAsyncStatus(asyncStatus: AsyncStatus): DeferredActionBuilder<T, E> {
        this.action.asyncStatus = asyncStatus;
        return this;
    }

    public withExtraArg(extra: E): DeferredActionBuilder<T, E> {
        this.action.extra = extra;
        return this;
    }

    public build(): DeferredAction<T, E> {
        return {...this.action};
    }
}