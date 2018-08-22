import * as React from "react";
import '../../css/app.css'
import StatusBar from "./StatusBar";
import CircularMenu from './CircularMenu';
import { Route } from 'react-router-dom'

export class App extends React.Component<{}> {
  render() {
    return (
      <div>
        {/* <StatusBar /> */}
          {/* <CircularMenu /> */}
          <Route exact path='/ggg' component={() => <b>111</b>} />
      </div>
    )
  }
}