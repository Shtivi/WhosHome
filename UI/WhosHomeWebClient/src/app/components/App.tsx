import * as React from "react";
import '../../css/app.css'
import StatusBar from "./StatusBar";
import { Route } from 'react-router-dom'
import NavMenu from "./NavMenu";
import WhosHomeMuiTheme from '../config/WhosHomeMuiTheme';
import { MuiThemeProvider } from '@material-ui/core/styles';

export class App extends React.Component<{}> {
  render() {
    return (
      <MuiThemeProvider theme={WhosHomeMuiTheme}>
        <div>
            <StatusBar />
            {/* <CircularMenu /> */}
            <Route exact path='/sensors' component={() => <b>sensors</b>} />
            <NavMenu />
        </div>
      </MuiThemeProvider>
    )
  }
}