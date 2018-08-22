import * as React from "react";
import { render } from "react-dom";
import { Provider } from "react-redux";
import { App } from "./components/App";
import { store } from './store/Store'
import { BrowserRouter as Router, Route, Switch, Link } from 'react-router-dom'

render(
  <Provider store={store}>
    <Router>
  <div>
    <Link to='/ggg'>ggg</Link>
      <Switch>
      
      <Route exact path='/ggg' component={() => <b>111</b>} />
      </Switch>
    </div>
    </Router>
  </Provider>,
  document.getElementById("root")
);