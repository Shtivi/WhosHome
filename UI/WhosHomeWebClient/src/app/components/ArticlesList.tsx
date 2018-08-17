import * as React from 'react'
import { Article } from '../models/Article';
import { Dispatch } from 'redux';
import { Action } from '../actions/Action';
import List from '@material-ui/core/List';
import { ArticleItem } from './ArticleItem';
import { removeArticle } from '../actions/ArticleActionCreators'
import { connect } from "react-redux";

interface ArticlesListProps {
    articles: Article[]
    dispatch: Dispatch<Action<Article>>
}

class ArticlesList extends React.Component<ArticlesListProps> {
    render() {
        return (
            <List>
                {this.props.articles.map(article => {
                    <ArticleItem key={article.id} article={article} onClick={() => this.props.dispatch(removeArticle(article))} />
                })}
            </List>
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