import { combineReducers } from 'redux';
import PushNotificationsReducer from './PushNotificationsReducer';

const rootReducer = combineReducers({
    push: PushNotificationsReducer
})

export default rootReducer;