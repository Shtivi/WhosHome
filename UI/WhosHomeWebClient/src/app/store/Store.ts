import { Store, createStore, applyMiddleware } from 'redux';
import rootReducer from '../reducers/RootReducer'
import { PushConnectionStatus } from '../models/PushConnectionStatus';
import pushSubsription from '../middleware/PushNotificationsSubscriber';
import { SensorConnection } from '../models/SensorConnection';

export interface PushNotificationsState {
    connectionStatus: PushConnectionStatus,
    error?: any
}

export interface SensorsState {
    sensors: { [id: number]: SensorConnection }
}

export const store: Store<any, any> = createStore(
    rootReducer,
    applyMiddleware(pushSubsription)
);

