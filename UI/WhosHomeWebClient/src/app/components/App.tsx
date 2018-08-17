import * as React from "react";
import Typography from '@material-ui/core/Typography';
import ArticlesList from "./ArticlesList";

export class App extends React.Component<{}, {}> {
  render() {
    return (
      <Typography variant="title" gutterBottom>
        <ArticlesList />
      </Typography>
    )
  }
}