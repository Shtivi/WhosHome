import { BaseAction } from "../actions/BaseAction";

export interface IReducer<A extends BaseAction> {
    invoke(state: {}, action: A): {};
}