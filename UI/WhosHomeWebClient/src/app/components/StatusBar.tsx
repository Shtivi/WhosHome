import * as React from "react";
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import { withStyles, createStyles, WithStyles } from '@material-ui/core/styles';
import PushNotifications from './PushNotifications';
import Avatar from '@material-ui/core/Avatar';

const styles = createStyles({
    appBar: {
        background: 'transparent',
        boxShadow: 'none'
    },
    rightSide: {
        marginLeft: 'auto'
    },
    avatar: {
        border: '1px solid #fff'
    }
});

interface StatusBarProps extends WithStyles<typeof styles> {}

class StatusBar extends React.Component<StatusBarProps> {
    render() {
        const {classes} = this.props;

        return (
            <AppBar className={classes.appBar} position="sticky">
                <Toolbar>
                    <Avatar className={classes.avatar}>IS</Avatar>
                    <div className={classes.rightSide}>
                        <PushNotifications />
                    </div>
                </Toolbar>
            </AppBar>
        )
    }
}

export default withStyles(styles)(StatusBar);