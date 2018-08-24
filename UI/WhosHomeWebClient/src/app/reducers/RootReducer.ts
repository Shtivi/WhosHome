import { combineReducers } from 'redux';
import PushNotificationsReducer from './PushNotificationsReducer';
import SensorsReducer from './SensorsReducer';

const rootReducer = combineReducers({
    push: PushNotificationsReducer,
    sensors: SensorsReducer
})

export default rootReducer;