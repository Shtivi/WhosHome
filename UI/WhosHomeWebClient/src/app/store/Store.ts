import { Store, createStore, applyMiddleware } from 'redux';
import rootReducer from '../reducers/RootReducer'
import { PushConnectionStatus } from '../models/PushConnectionStatus';
import pushSubsription from '../middleware/PushNotificationsSubscriber';

export interface PushNotificationsState {
    connectionStatus: PushConnectionStatus,
    error?: any
}

export const store: Store<any, any> = createStore(
    rootReducer,
    applyMiddleware(pushSubsription)
);

