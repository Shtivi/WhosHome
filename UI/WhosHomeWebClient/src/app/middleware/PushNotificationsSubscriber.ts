import { Action, Store, Dispatch } from "redux";
import { ActionTypes } from "../actions/ActionTypes";
import * as SockJS from 'sockjs-client'
import * as Stomp from "@stomp/stompjs"
import { 
    pushNotificationsSubscribed, 
    subscribingPush, pushDisconnected, 
    pushSubscriptionError,
    pushReceived
} from "../actions/PushNotificationsActionCreators";
import SensorStatusChanged from "../models/eventArgs/SensorStatusChanged";
import SensorError from "../models/eventArgs/SensorError";
import EngineStatusChanged from "../models/eventArgs/EngineStatusChanged";
import PushNotification, { NotificationType } from "../models/PushNotification";
import config from '../config/config'

let client: Stomp.Client = null;
let dispatch: Dispatch = null;

const pushSubsription = (store: Store) => (next: any) => (action: Action<ActionTypes>) => {
    dispatch = store.dispatch;

    switch(action.type) {
        case ActionTypes.SUBSCRIBE_PUSH: 
            let socket = new SockJS(config.push.endpoint);
            client = Stomp.over(socket);
            store.dispatch(subscribingPush());
            client.connect({}, handleConnection, handleError, handleDisconnection);
            break;
        case ActionTypes.UNSUBSCRIBE_PUSH:
            if (client != null) {
                client.disconnect(handleDisconnection);
            }
            break;
    }
    next(action);
}

const handleConnection = () => {
    client.subscribe(config.push.topics.sensorsStatus, (message: Stomp.Message) => {
        let eventArgs: SensorStatusChanged = <SensorStatusChanged>(JSON.parse(message.body));
        dispatch(pushReceived(PushNotification.of(NotificationType.SENSOR_STATUS_CHANGED, eventArgs)))
    })
    
    client.subscribe(config.push.topics.sensorError, (message: Stomp.Message) => {
        let eventArgs: SensorError = <SensorError>(JSON.parse(message.body));
        dispatch(pushReceived(PushNotification.of(NotificationType.SENSOR_ERROR, eventArgs)))
    })

    client.subscribe(config.push.topics.engineStatus, (message: Stomp.Message) => {
        let eventArgs: EngineStatusChanged = <EngineStatusChanged>(JSON.parse(message.body));
        dispatch(pushReceived(PushNotification.of(NotificationType.ENGINE_STATUS_CHANGED, eventArgs)))
    })

    client.subscribe(config.push.topics.peopleDetection, (message: Stomp.Message) => {
        // console.warn('implement a subscriber for people detection!');
        console.log(message.body);
    })

    dispatch(pushNotificationsSubscribed());
}

const handleDisconnection = (event?: CloseEvent) => {
    dispatch(pushDisconnected(event));
}

const handleError = (error: string) => {
    dispatch(pushSubscriptionError(error));
}

export default pushSubsription;