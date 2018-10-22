import { Store, createStore, applyMiddleware } from 'redux';
import rootReducer from '../reducers/RootReducer'
import { PushConnectionStatus } from '../models/PushConnectionStatus';
import pushSubsription from '../middleware/PushNotificationsSubscriber';
import promiseResolver from '../middleware/PromiseResolver';
import { SensorConnection } from '../models/SensorConnection';
import PushNotification from '../models/PushNotification';
import PersonPresenceData from '../models/PersonPresenceData';
import ActivityDetectionEventArgs from '../models/eventArgs/ActivityDetectionEventArgs';

export interface PushNotificationsState {
    connectionStatus: PushConnectionStatus,
    error?: any,
    notificationsLog: PushNotification<any>[]
}

export interface SensorsState {
    sensors: { [id: number]: SensorConnection }
}

export interface PresenceState {
    recognized: PersonPresenceData[]
}

export const store: Store<any, any> = createStore(
    rootReducer,
    applyMiddleware(pushSubsription, promiseResolver)
);

