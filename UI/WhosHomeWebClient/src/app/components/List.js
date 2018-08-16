import React from "react";
import { connect } from "react-redux";
import { removeArticle } from "../actions";
import { Item } from './Item'

const mapStateToProps = state => {
  return { articles: state.articles };
};

const mapDispatchToProps = dispatch => {
  return {
    removeArticle: id => dispatch(removeArticle(id))
  }
}

const ConnectedList = ({ articles, removeArticle }) => (
  <ul className="list-group list-group-flush">
    {Object.keys(articles).map(el => 
      <Item key={el} article={articles[el]} 
            onClick={() => removeArticle(el)} />
    )}
  </ul>
);

const List = connect(mapStateToProps, mapDispatchToProps)(ConnectedList);
export default List;