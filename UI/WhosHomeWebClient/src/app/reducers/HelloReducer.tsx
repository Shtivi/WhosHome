import { BaseAction } from "../actions/BaseAction";
import { HelloAction } from "../actions/HelloAction";
import { IReducer } from "./IReducer";

export class HelloReducer implements IReducer<HelloAction> {
    public invoke(state: {}, action: HelloAction): any {
        return null;
    }
}