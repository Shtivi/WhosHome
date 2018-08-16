import { ADD_ARTICLE, REMOVE_ARTICLE } from '../constants/action-types';


const articles = (state = {}, action) => {
    switch (action.type) {
        case ADD_ARTICLE:
            let newArticles = {...state};
            newArticles[action.payload.id] = action.payload;
            return newArticles;
        case REMOVE_ARTICLE:
            let updatedArticles = {...state};
            delete updatedArticles[action.payload.id];
            return updatedArticles;
        default:
            return state;
    }
}

export default articles