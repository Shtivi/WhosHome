import { SensorsState } from "../store/Store";
import { ActionTypes } from "../actions/ActionTypes";
import SensorStatusChanged from "../models/eventArgs/SensorStatusChanged";

const initialState: SensorsState = {
    sensors: {}
}

export default (state: SensorsState = initialState, action: {type: ActionTypes, payload: any}) => {
    switch (action.type) {
        case ActionTypes.SENSOR_STATUS_CHANGED: 
            let sensorsState: SensorsState = {...state};
            let eventArgs: SensorStatusChanged = (<SensorStatusChanged> action.payload);
            // todo: implement - change the state according to new status
            return sensorsState;
        default:
            return state;
    }
}