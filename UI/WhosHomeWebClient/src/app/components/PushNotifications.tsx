import * as React from "react";
import { PushConnectionStatus } from "../models/PushConnectionStatus";
import { Dispatch, Action } from 'redux';
import { PushNotificationsState } from "../store/Store";
import { connect } from "react-redux";
import SvgIcon from '@material-ui/core/SvgIcon';
import IconButton from '@material-ui/core/IconButton';
import CircularProgress from '@material-ui/core/CircularProgress';
import Notifications from '@material-ui/icons/NotificationsOutlined';
import NotificationsOff from '@material-ui/icons/NotificationsOffOutlined';
import NotificationImportant from '@material-ui/icons/NotificationImportantOutlined';
import { subscribePushNotifications, unsubsribePushNotifications } from "../actions/PushNotificationsActionCreators";

interface PushNotificationsProps {
    pushStatus: PushNotificationsState
    dispatch: Dispatch<Action>
}

class PushNotifications extends React.Component<PushNotificationsProps> {
    renderIcon() {
        let { pushStatus } = this.props;
        switch (pushStatus.connectionStatus) {
            case PushConnectionStatus.CONNECTED:
                return <SvgIcon style={{fontSize: '32px'}} onClick={() => this.props.dispatch(unsubsribePushNotifications())}><Notifications></Notifications></SvgIcon>
            case PushConnectionStatus.CONNECTING:
                return <CircularProgress />
            case PushConnectionStatus.DISCONNECTED:
                return <SvgIcon style={{fontSize: '32px'}} onClick={() => this.props.dispatch(subscribePushNotifications())}><NotificationsOff></NotificationsOff></SvgIcon>
            case PushConnectionStatus.ERROR:
                return <SvgIcon style={{fontSize: '32px'}} color='error' onClick={() => this.props.dispatch(subscribePushNotifications())}><NotificationImportant></NotificationImportant></SvgIcon>
        }
    }

    render() {
        const icon = this.renderIcon();
        return (
            <IconButton>
                {icon}
            </IconButton>
        );
    }
}

const mapStateToProps = (state: any) => ({
    pushStatus: state.push
})


const mapDispatchToProps = (dispatch: Dispatch<Action<any>>) => ({
    dispatch: dispatch
})

export default connect(mapStateToProps, mapDispatchToProps)(PushNotifications);