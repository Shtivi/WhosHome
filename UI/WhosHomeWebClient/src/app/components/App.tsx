import * as React from "react";
import Grid from '@material-ui/core/Grid';
import '../../css/app.css'
import Paper from '@material-ui/core/Paper'; 
import PushNotifications from './PushNotifications'

export class App extends React.Component<{}> {
  render() {
    var comp = (
      <Grid container spacing={0} justify='center' alignItems='center' className='main-container'>
        <Grid item xs={12} lg={6}>
          <Paper elevation={12} square>
            <PushNotifications />
          </Paper>
        </Grid>
      </Grid>
    )
    return comp;
  }
}