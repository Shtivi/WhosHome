import * as React from "react";
import '../../css/app.css'
import '../../fonts/fonts.css'
import StatusBar from "./StatusBar";
import { Route } from 'react-router-dom'
import NavMenu from "./NavMenu";
import WhosHomeMuiTheme from '../config/WhosHomeMuiTheme';
import { MuiThemeProvider, Theme } from '@material-ui/core/styles';
import SensorsPage from "./SensorsPage";
import { withStyles, createStyles, WithStyles } from '@material-ui/core/styles';
import bg from '../../pics/bg_v4/1.png'

const styles = (theme: Theme) => createStyles({
  root: {
    backgroundImage: "url('/pics/1.png')",
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100%',
    height: '100%'
  }
})

interface Props extends WithStyles<typeof styles> {}

class App extends React.Component<Props> {
  render() {
    const {classes} = this.props;

    return (
      <MuiThemeProvider theme={WhosHomeMuiTheme}>
        <div className={classes.root}>
            <StatusBar />
            <Route exact path='/sensors' component={() => <SensorsPage />} />
            <NavMenu />
        </div>
      </MuiThemeProvider>
    )
  }
}

export default withStyles(styles)(App);