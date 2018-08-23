import * as React from "react";
import { render } from "react-dom";
import { Provider } from "react-redux";
import { App } from "./components/App";
import { store } from './store/Store'
import { BrowserRouter as Router } from 'react-router-dom'

render(
  <Provider store={store}>
    <Router>
      <App />
    </Router>
  </Provider>,
  document.getElementById("root")
);