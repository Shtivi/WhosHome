import { ActionTypes, AsyncStatus } from "./ActionTypes";
import { Action } from "redux";
import { Promise } from "es6-promise";

export interface DeferredAction<R> extends Action<ActionTypes> {
    promise?: Promise<R>,
    asyncStatus: AsyncStatus,
    payload?: R,
    error?: Error
}

export class DeferredActionBuilder<T> {
    private action: DeferredAction<T>;

    public static from <T>(originalAction: Action<ActionTypes>): DeferredActionBuilder<T> {
        return new DeferredActionBuilder<T>(originalAction.type);
    }

    public static of <T>(type: ActionTypes): DeferredActionBuilder<T> {
        return new DeferredActionBuilder<T>(type);
    }

    public constructor(type: ActionTypes, asyncStatus?: AsyncStatus) {
        this.action = {
            type: type,
            asyncStatus: asyncStatus | AsyncStatus.PENDING
        }
    }

    public withPromise(promise: Promise<T>): DeferredActionBuilder<T> {
        this.action.promise = promise;
        return this;
    }

    public withPayload(payload: T): DeferredActionBuilder<T> {
        this.action.payload = payload;
        return this;
    }

    public withError(error: Error): DeferredActionBuilder<T> {
        this.action.error = error;
        return this;
    }

    public withAsyncStatus(asyncStatus: AsyncStatus): DeferredActionBuilder<T> {
        this.action.asyncStatus = asyncStatus;
        return this;
    }

    public build(): DeferredAction<T> {
        return this.action;
    }
}