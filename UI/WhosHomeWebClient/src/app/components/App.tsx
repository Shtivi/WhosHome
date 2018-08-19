import * as React from "react";
import Grid from '@material-ui/core/Grid';
import '../../css/app.css'
import Paper from '@material-ui/core/Paper'; 
import StatusBar from "./StatusBar";
import CircularMenu from './CircularMenu';

export class App extends React.Component<{}> {
  render() {
    var comp = (
      // <Grid container spacing={0} justify='center' alignItems='center' className='main-container'>
      //   <Grid item xs={12} lg={6}>
      //     <Paper elevation={12} square>
      //       <StatusBar />
      //     </Paper>
      //   </Grid>
      // </Grid>
      <div>
        {/* <StatusBar /> */}
        <CircularMenu />
      </div>
    )
    return comp;
  }
}