import { Article } from "../models/Article";
import * as React from 'react'
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';

interface ArticleItemProps {
    article: Article,
    onClick: () => void
}

export class ArticleItem extends React.Component<ArticleItemProps> {
    render() {
        return (
            <ListItem onClick={() => this.props.onClick}>
                <ListItemText>
                    {this.props.article.id} <b>{this.props.article.title}</b>
                </ListItemText>
            </ListItem>
        )
    }
}