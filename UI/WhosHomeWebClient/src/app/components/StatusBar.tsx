import * as React from "react";
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import { withStyles, createStyles, WithStyles } from '@material-ui/core/styles';
import PushNotifications from './PushNotifications';

const styles = createStyles({
    appBar: {
        backgroundColor: '#333'
    }
});

interface StatusBarProps extends WithStyles<typeof styles> {}

class StatusBar extends React.Component<StatusBarProps> {
    render() {
        return (
            <AppBar className={this.props.classes.appBar}>
                <Toolbar>
                    <PushNotifications />
                </Toolbar>
            </AppBar>
        )
    }
}

export default withStyles(styles)(StatusBar);