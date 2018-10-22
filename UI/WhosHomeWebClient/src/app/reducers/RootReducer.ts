import { combineReducers } from 'redux';
import PushNotificationsReducer from './PushNotificationsReducer';
import SensorsReducer from './SensorsReducer';
import PresenceReducer from './PresenceReducer';

const rootReducer = combineReducers({
    push: PushNotificationsReducer,
    sensors: SensorsReducer,
    presence: PresenceReducer
})

export default rootReducer;