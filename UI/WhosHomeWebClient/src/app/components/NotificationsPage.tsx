import * as React from "react";
import { withStyles, createStyles, WithStyles, Theme } from '@material-ui/core/styles';
import { connect } from 'react-redux'
import { Dispatch, Action } from 'redux'
import { PushNotificationsState } from "../store/Store";
import SvgIcon from '@material-ui/core/SvgIcon';
import Notifications from '@material-ui/icons/NotificationsOutlined';
import PushNotification from "../models/PushNotification";

const styles = (theme: Theme) => createStyles({
    root: {
    },
    notificationsList: {
    },
    notificationsListItem: {
        width: '100%',
        display: 'flex',
        flexDirection: 'row',
        alignContent: 'center',
        borderTop: '1px solid #e9e9e9',
        borderBottom: '1px solid #e9e9e9',

    }
})

interface NotificationsPageProps extends WithStyles<typeof styles> {
    dispatch: Dispatch<Action>,
    notifcationsState: PushNotificationsState
}

interface NotificationsPageState {
}

const mapStateToProps = (state: any) => ({
    notifcationsState: state.push
})

const mapDispatchToProps = (dispatch: Dispatch<Action<any>>) => ({
    dispatch: dispatch
})

class NotificationsPage extends React.Component<NotificationsPageProps, NotificationsPageState> {
    public constructor(props: NotificationsPageProps) {
        super(props);
    }

    private notificationsComparator(pushA: PushNotification<any>, pushB: PushNotification<any>): number {
        return pushA.receiveTime().getTime() - pushB.receiveTime().getTime();
    }

    render() {
        const {classes} = this.props;
        const {notifcationsState} = this.props;
        const {notificationsComparator} = this;

        return (
            <div className={classes.root}>
                <div className={classes.notificationsList}>
                    {notifcationsState.notificationsLog.sort(notificationsComparator).map((notification, i) => {
                        return (
                            <div key={i} className={classes.notificationsListItem}>
                                <SvgIcon><Notifications></Notifications></SvgIcon>
                                {notification.notificationType()}
                                {notification.receiveTime().toLocaleString()}
                            </div>
                        )
                    })}
                </div>
            </div>
        )
    }
}

export default withStyles(styles)(connect(mapStateToProps, mapDispatchToProps)(NotificationsPage));