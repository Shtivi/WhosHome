import { PushNotificationsState } from "../store/Store";
import { PushConnectionStatus } from "../models/PushConnectionStatus";
import { ActionTypes } from "../actions/ActionTypes";

const initialState: PushNotificationsState = {
    connectionStatus: PushConnectionStatus.DISCONNECTED,
    notificationsLog: []
}

export default (state: PushNotificationsState = initialState, action: {type: ActionTypes, payload: any}) =>{
    switch (action.type) {
        case ActionTypes.SUBSCRIBING_PUSH: 
            var updatedState: PushNotificationsState = {...state};
            updatedState.connectionStatus = PushConnectionStatus.CONNECTING;
            updatedState.error = null;
            return updatedState;

        case ActionTypes.PUSH_SUBSCRIBED_SUCCESSFULLY: 
            var updatedState: PushNotificationsState = {...state};
            updatedState.connectionStatus = PushConnectionStatus.CONNECTED;
            updatedState.error = null;
            return updatedState;

        case ActionTypes.PUSH_SUBSCRIPTION_ERROR: 
            var updatedState: PushNotificationsState = {...state};
            updatedState.connectionStatus = PushConnectionStatus.ERROR;
            updatedState.error = action.payload;
            return updatedState;

        case ActionTypes.PUSH_DISCONNECTED: 
            var updatedState: PushNotificationsState = {...state};
            updatedState.connectionStatus = PushConnectionStatus.DISCONNECTED;
            updatedState.error = null;
            return updatedState;

        case ActionTypes.PUSH_RECEIVED: 
            var updatedState: PushNotificationsState = {...state};
            updatedState.notificationsLog.push(action.payload);
            return updatedState;
        default:
            return state;
    }
}