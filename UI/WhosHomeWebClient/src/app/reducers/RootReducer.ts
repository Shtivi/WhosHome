import { combineReducers } from 'redux';
import ArticlesReducer from './ArticlesReducer'

const rootReducer = combineReducers({
    articles: ArticlesReducer
})

export default rootReducer;