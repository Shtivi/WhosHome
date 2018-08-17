import * as React from "react";
import { render } from "react-dom";
import { Provider } from "react-redux";
import { App } from "./components/App";
import { Store, createStore } from 'redux';
import { Action } from "./actions/Action";
import rootReducer from './reducers/RootReducer'

const store: Store<any, Action<any>> = createStore(rootReducer);

render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById("root")
);