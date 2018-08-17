import { ActionTypes } from "./ActionTypes";
import { Action } from "./Action";

export interface IActionCreator<T> {
    (data: T): Action<T>;
}