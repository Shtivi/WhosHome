import * as React from 'react'
import { Article } from '../models/Article';
import { Dispatch } from 'redux';
import { Action } from 'redux';
import List from '@material-ui/core/List';
import { ArticleItem } from './ArticleItem';
import { removeArticle } from '../actions/ArticleActionCreators'
import { connect } from "react-redux";
import { ArticlesState } from '../reducers/ArticlesReducer';

interface ArticlesListProps {
    articles: ArticlesState
    dispatch: Dispatch<Action>
}

class ArticlesList extends React.Component<ArticlesListProps> {
    render() {
        const { articles, dispatch } = this.props;
        return (
            <div>
            <ul>
                {Object.keys(articles).map((id) => {
                    let articleID: number = Number(id);
                    let article = articles[articleID];
                    return <ArticleItem key={articleID} article={article} onClick={() => dispatch(removeArticle(article))} />
                })}
            </ul>
            </div>
        )
    }
}

// todo: state with types???
const mapStateToProps = (state: any) => ({
    articles: state.articles
})

const mapDispatchToProps = (dispatch: Dispatch<Action<Article>>) => ({
    dispatch: dispatch
})

export default connect(mapStateToProps, mapDispatchToProps)(ArticlesList);