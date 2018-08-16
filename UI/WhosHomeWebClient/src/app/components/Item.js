import React, { Component } from "react";

export const Item = ({ onClick, article }) => {
    return (
        <li onClick={onClick}>
            #{article.id} <b>{article.title}</b>
        </li>
    );
}