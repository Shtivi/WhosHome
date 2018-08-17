import { ActionTypes } from "./ActionTypes";
import { Action as BaseAction } from 'redux'

export class Action<T> implements BaseAction<ActionTypes> {
    public readonly type: ActionTypes;
    public payload: T;

    public constructor(type: ActionTypes, payload?: T) {
        this.type = type;
        this.payload = payload;
    }

    public withPayload(payload: T): Action<T> {
        this.payload = payload;
        return this;
    }
}