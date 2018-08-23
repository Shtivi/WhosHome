import { Action, Store, Dispatch } from "redux";
import { ActionTypes } from "../actions/ActionTypes";
import * as SockJS from 'sockjs-client'
import * as Stomp from "@stomp/stompjs"
import { 
    pushNotificationsSubscribed, 
    subscribingPush, pushDisconnected, 
    pushSubscriptionError,
    sensorStatusChanged,
    sensorError,
    engineStatusChanged
} from "../actions/PushNotificationsActionCreators";
import SensorStatusChanged from "../models/eventArgs/SensorStatusChanged";
import SensorError from "../models/eventArgs/SensorError";
import EngineStatusChanged from "../models/eventArgs/EngineStatusChanged";

let client: Stomp.Client = null;
let dispatch: Dispatch = null;

const pushSubsription = (store: Store) => (next: any) => (action: Action<ActionTypes>) => {
    dispatch = store.dispatch;

    switch(action.type) {
        case ActionTypes.SUBSCRIBE_PUSH: 
            let socket = new SockJS('http://localhost:9000/push');
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
    client.subscribe('/topics/sensors/status', (message: Stomp.Message) => {
        console.log(message.body);
        let parsedMessage: SensorStatusChanged = <SensorStatusChanged>(JSON.parse(message.body));
        dispatch(sensorStatusChanged(parsedMessage));
    })
    
    client.subscribe('/topics/sensors/error', (message: Stomp.Message) => {
        console.log(message.body);
        let parsedMessage: SensorError = <SensorError>(JSON.parse(message.body));
        dispatch(sensorError(parsedMessage));
    })

    client.subscribe('/topics/engine/status', (message: Stomp.Message) => {
        console.log(message.body);
        let parsedMessage: EngineStatusChanged = <EngineStatusChanged>(JSON.parse(message.body));
        dispatch(engineStatusChanged(parsedMessage));
    })

    client.subscribe('/topics/people/detection', (message: Stomp.Message) => {
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