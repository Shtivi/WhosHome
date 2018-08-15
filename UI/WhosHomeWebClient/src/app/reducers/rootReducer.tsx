import { combineReducers } from "../../../node_modules/redux";
import { HelloReducer } from "./HelloReducer";

export default combineReducers({
    helloReducer: new HelloReducer().invoke
})