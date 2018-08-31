import * as React from "react";
import '../../css/app.css'
import '../../fonts/fonts.css'
import StatusBar from "./StatusBar";
import { Route, withRouter, RouteComponentProps } from 'react-router-dom'
import NavMenu from "./NavMenu";
import WhosHomeMuiTheme from '../config/WhosHomeMuiTheme';
import { MuiThemeProvider, Theme } from '@material-ui/core/styles';
import SensorsPage from "./SensorsPage";
import { withStyles, createStyles, WithStyles } from '@material-ui/core/styles';
import { Dispatch, Action } from "redux";
import { connect } from 'react-redux'
import { subscribePushNotifications } from "../actions/PushNotificationsActionCreators";

const styles = (theme: Theme) => createStyles({
  root: {
    backgroundImage: "url('/pics/1.png')",
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100%',
    height: '100%'
  }
})

interface Props extends WithStyles<typeof styles>, RouteComponentProps<any> {
  dispatch: Dispatch<Action>,
}

class App extends React.Component<Props> {
  public constructor(props: Props) {
    super(props);
    this.props.dispatch(subscribePushNotifications());
  }

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

const mapDispatchToProps = (dispatch: Dispatch<Action<any>>) => ({
  dispatch: dispatch
})

export default withStyles(styles)(
  withRouter(
    connect(null, mapDispatchToProps)(App)
  )
);