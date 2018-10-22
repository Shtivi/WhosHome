import { PresenceState } from "../store/Store";
import { Action } from "redux";
import { ActionTypes, AsyncStatus } from "../actions/ActionTypes";
import { DeferredAction } from "../actions/DeferredAction";
import PersonPresenceData from "../models/PersonPresenceData";
import PushNotification, { NotificationType } from "../models/PushNotification";
import PersonActivityEventArgs from "../models/eventArgs/PersonActivityEventArgs";

const initialState: PresenceState = {
    recognized: []
}

export default (state: PresenceState = initialState, action: Action<ActionTypes> | DeferredAction<PersonPresenceData[]>) => {
    switch (action.type) {
        case ActionTypes.PUSH_RECEIVED:
            return handlePush(state, (<any>action).payload);
        case ActionTypes.FETCH_PRESENCE_DATA:
            let deferredAction: DeferredAction<PersonPresenceData[]> = <DeferredAction<PersonPresenceData[]>>action;
            return handlePresenceDataFetching(state, deferredAction.payload, deferredAction.asyncStatus);
        default:
            return state;
    }
}

const handlePush = (currentState: PresenceState, notification: PushNotification<any>): PresenceState => {
    if (notification.notificationType() == NotificationType.ACTIVITY_DETECTION) { 
        console.log(notification);
        let updatedState = {...currentState};
        let payload: PersonActivityEventArgs = <PersonActivityEventArgs> notification.payload();
        updatedState.recognized
        return updatedState;
    }

    return currentState;
}

const handlePresenceDataFetching = (currentState: PresenceState, payload: PersonPresenceData[], status: AsyncStatus): PresenceState => {
    console.log(payload);
    switch(status) {
        case AsyncStatus.COMPLETED: 
            let updatedState = {...currentState};
            updatedState.recognized = payload;
            return updatedState;
        default:
            return currentState;
    }
}