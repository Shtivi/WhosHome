import { ActionTypes } from "./ActionTypes";
import SensorStatusChanged from "../models/eventArgs/SensorStatusChanged";
import SensorError from "../models/eventArgs/SensorError";
import EngineStatusChanged from "../models/eventArgs/EngineStatusChanged";
import PushNotification from "../models/PushNotification";

export const subscribePushNotifications = () => ({
    type: ActionTypes.SUBSCRIBE_PUSH
})

export const unsubsribePushNotifications = () => ({
    type: ActionTypes.UNSUBSCRIBE_PUSH
})

export const pushNotificationsSubscribed = () => ({
    type: ActionTypes.PUSH_SUBSCRIBED_SUCCESSFULLY
})

export const subscribingPush = () => ({
    type: ActionTypes.SUBSCRIBING_PUSH
})

export const pushSubscriptionError = (error: any) => ({
    type: ActionTypes.PUSH_SUBSCRIPTION_ERROR,
    payload: error
})

export const pushDisconnected = (reason?: any) => ({
    type: ActionTypes.PUSH_DISCONNECTED,
    payload: reason
})

export const pushReceived = (pushNotification: PushNotification<any>) => ({
    type: ActionTypes.PUSH_RECEIVED,
    payload: pushNotification
})
