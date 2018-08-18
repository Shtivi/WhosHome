import { ActionTypes } from "./ActionTypes";

export const subscribePushNotifications = () => ({
    type: ActionTypes.SUBSCRIBE_PUSH
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