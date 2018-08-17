import {Article} from '../models/Article'
import { ActionTypes } from '../actions/ActionTypes';

export interface ArticlesState {
    [id: number]: Article
}

const initialState: ArticlesState = {
    1: new Article(1, 'Hello World')
}

export default (state: ArticlesState = initialState, action: {type: ActionTypes, payload: Article}) => {
    switch (action.type) {
        case ActionTypes.ADD_ARTICLE: {
            let updatedState: ArticlesState = {...state};
            updatedState[action.payload.id] = action.payload;
            return updatedState;
        }
        case ActionTypes.REMOVE_ARTICLE: {
            let updatedState: ArticlesState = {...state};
            delete updatedState[action.payload.id];
            return updatedState;
        }
        default: {
            return state;
        }
    }
}