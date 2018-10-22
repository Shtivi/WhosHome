import { SensorsState } from "../store/Store";
import { ActionTypes, AsyncStatus } from "../actions/ActionTypes";
import SensorStatusChanged from "../models/eventArgs/SensorStatusChanged";
import PushNotification, { NotificationType } from "../models/PushNotification";
import { SensorConnection } from "../models/SensorConnection";
import { DeferredAction } from "../actions/DeferredAction";
import { Action } from "redux";

const initialState: SensorsState = {
    sensors: {}
}

export default (state: SensorsState = initialState, action: Action<ActionTypes> | DeferredAction<SensorConnection[]>) => {
    switch (action.type) {
        case ActionTypes.PUSH_RECEIVED: 
            return handlePush(state, (<any>action).payload);
        case ActionTypes.FETCH_SENSORS:
            let deferredAction: DeferredAction<SensorConnection[]> = <DeferredAction<SensorConnection[]>>action;
            console.log(action);
            return handleSensorsFetching(state, deferredAction.payload, deferredAction.asyncStatus);
        default:
            return state;
    }
}

const handlePush = (currentState: SensorsState, notification: PushNotification<any>): SensorsState => {
    let updatedState: SensorsState = {...currentState};

    switch (notification.notificationType()) {
        case NotificationType.SENSOR_STATUS_CHANGED:
            let payload: SensorStatusChanged = <SensorStatusChanged>notification.payload();
            let updatedState = {...currentState};
            updatedState.sensors[payload.sensorConnectionMetadata.sensorConnectionID].status = payload.newStatus;
            return updatedState;
        case NotificationType.SENSOR_ERROR: 
            console.warn(notification);
            break;
        default:
            break;
    }

    return updatedState;
}

const handleSensorsFetching = (currentState: SensorsState, payload: SensorConnection[], status: AsyncStatus): SensorsState => {
    switch (status) {
        case AsyncStatus.COMPLETED: 
            let updatedState = {...currentState};
            updatedState.sensors = {};
            payload.forEach(sensor => updatedState.sensors[sensor.connectionMetadata.sensorConnectionID] = sensor);
            return updatedState;
        default:
            return currentState;
    }
}