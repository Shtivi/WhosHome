import {Article} from '../models/Article'
import { Action } from '../actions/Action';
import { ActionTypes } from '../actions/ActionTypes';

const initialState: Article[] = [new Article(1, 'Hello World')];

export default (state: Article[] = initialState, action: Action<Article>) => {
    switch (action.type) {
        case ActionTypes.ADD_ARTICLE: {
            let updatedState: Article[] = [...state];
            updatedState.push(action.payload);
            return updatedState;
        }
        default: {
            return state;
        }
    }
}