import { Article } from "../models/Article";
import { ActionTypes } from "./ActionTypes";

export const removeArticle = (article: Article) => ({
    type: ActionTypes.REMOVE_ARTICLE,
    payload: article
})