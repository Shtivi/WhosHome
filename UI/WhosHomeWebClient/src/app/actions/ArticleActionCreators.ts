import { Article } from "../models/Article";
import { Action } from "./Action";
import { ActionTypes } from "./ActionTypes";
import { IActionCreator } from "./IActionCreator";

export const addArticle: IActionCreator<Article> = (data: Article) => {
    return new Action<Article>(ActionTypes.ADD_ARTICLE).withPayload(data);
}

export const removeArticle: IActionCreator<Article> = (data: Article) => {
    return new Action<Article>(ActionTypes.REMOVE_ARTICLE).withPayload(data);
}