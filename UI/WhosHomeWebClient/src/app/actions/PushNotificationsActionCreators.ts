import { ActionTypes } from "./ActionTypes";
import SensorStatusChanged from "../models/eventArgs/SensorStatusChanged";
import SensorError from "../models/eventArgs/SensorError";
import EngineStatusChanged from "../models/eventArgs/EngineStatusChanged";

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

export const sensorStatusChanged = (eventArgs: SensorStatusChanged)  => ({
    type: ActionTypes.SENSOR_STATUS_CHANGED,
    payload: eventArgs
})

export const sensorError = (eventArgs: SensorError) => ({
    type: ActionTypes.SENSOR_ERROR,
    payload: eventArgs
})

export const engineStatusChanged = (eventArgs: EngineStatusChanged) => ({
    type: ActionTypes.ENGINE_STATUS_CHANGED,
    payload: eventArgs
})