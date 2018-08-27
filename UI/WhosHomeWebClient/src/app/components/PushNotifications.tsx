import * as React from "react";
import { PushConnectionStatus } from "../models/PushConnectionStatus";
import { Dispatch, Action } from 'redux';
import { PushNotificationsState } from "../store/Store";
import SvgIcon from '@material-ui/core/SvgIcon';
import Badge from '@material-ui/core/Badge';
import IconButton from '@material-ui/core/IconButton';
import CircularProgress from '@material-ui/core/CircularProgress';
import Notifications from '@material-ui/icons/NotificationsOutlined';
import NotificationsOff from '@material-ui/icons/NotificationsOffOutlined';
import NotificationImportant from '@material-ui/icons/NotificationImportantOutlined';
import { subscribePushNotifications, unsubsribePushNotifications } from "../actions/PushNotificationsActionCreators";
import { connect } from 'react-redux'
import { withStyles, createStyles, WithStyles, Theme } from '@material-ui/core/styles';
import Zoom from '@material-ui/core/Zoom';
import Snackbar from '@material-ui/core/Snackbar';

const styles = (theme: Theme) => createStyles({
    margin: {
        margin: theme.spacing.unit * 2,
    }    
})

interface PushNotificationsProps extends WithStyles<typeof styles> {
    pushState: PushNotificationsState
    dispatch: Dispatch<Action>
}

class PushNotifications extends React.Component<PushNotificationsProps> {
    renderIcon() {
        let { pushState } = this.props;
        switch (pushState.connectionStatus) {
            case PushConnectionStatus.CONNECTED:
                return <SvgIcon style={{fontSize: '32px'}} onClick={() => this.props.dispatch(unsubsribePushNotifications())}><Notifications></Notifications></SvgIcon>
            case PushConnectionStatus.CONNECTING:
                return <CircularProgress />
            case PushConnectionStatus.DISCONNECTED:
                return <SvgIcon style={{fontSize: '32px'}} onClick={() => this.props.dispatch(subscribePushNotifications())}><NotificationsOff></NotificationsOff></SvgIcon>
            case PushConnectionStatus.ERROR:
                return (
                    <SvgIcon style={{fontSize: '32px'}} color='error' onClick={() => this.props.dispatch(subscribePushNotifications())}><NotificationImportant></NotificationImportant></SvgIcon>
                )
        }
    }

    render() {
        const {classes, pushState} = this.props;
        const icon = this.renderIcon();
        return (
                (pushState.connectionStatus == PushConnectionStatus.CONNECTED && pushState.notificationsLog.length > 0) ? 
                    (
                        <IconButton>
                            <Zoom in={true}>
                                <Badge className={classes.margin} color='secondary' badgeContent={this.props.pushState.notificationsLog.length}>
                                    {icon}
                                </Badge>
                            </Zoom>
                        </IconButton>
                    ) :
                    (
                        <IconButton>{icon}</IconButton>
                        // (pushState.connectionStatus == PushConnectionStatus.ERROR) ? 
                        // (
                        //     <Snackbar open={false} anchorOrigin={{vertical: 'top', horizontal: 'left'}} message={pushState.error} />
                        // ) 
                    )
        );  
    }
}

const mapStateToProps = (state: any) => ({
    pushState: state.push
})


const mapDispatchToProps = (dispatch: Dispatch<Action<any>>) => ({
    dispatch: dispatch
})

export default withStyles(styles)(connect(mapStateToProps, mapDispatchToProps)(PushNotifications));