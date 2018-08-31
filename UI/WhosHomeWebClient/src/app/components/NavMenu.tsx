import * as React from "react";
import BottomNavigation from '@material-ui/core/BottomNavigation';
import BottomNavigationAction from '@material-ui/core/BottomNavigationAction';
import { withStyles, createStyles, WithStyles, Theme } from '@material-ui/core/styles';
import Router from '@material-ui/icons/RouterOutlined';
import People from '@material-ui/icons/PeopleOutlined';
import Settings from '@material-ui/icons/SettingsOutlined';
import Notifications from '@material-ui/icons/NotificationsOutlined';
import NotificationsOff from '@material-ui/icons/NotificationsOffOutlined';
import NotificationImportant from '@material-ui/icons/NotificationImportantOutlined';
import { NavLink, Link, RouteComponentProps, withRouter } from 'react-router-dom'
import { PushNotificationsState } from "../store/Store";
import { Action, Dispatch } from "redux";
import { connect } from "react-redux";
import { PushConnectionStatus } from "../models/PushConnectionStatus";
import { CircularProgress } from "@material-ui/core";
import Zoom from '@material-ui/core/Zoom';
import Badge from '@material-ui/core/Badge';

const styles = (theme: Theme) => createStyles({
    root: {
        borderTop: '1px solid #e9e9e9',
        position: 'fixed',
        bottom: '0px',
        width: '100%'
    }, 
    navIcon: {
        fontSize: '36px'
    },
    margin: {
        margin: theme.spacing.unit * 2,
    }  
});

interface NavMenuProps extends WithStyles<typeof styles>, RouteComponentProps<any> {
    pushState: PushNotificationsState
    dispatch: Dispatch<Action>
}

interface NavMenuState {
    value: any
}

class NavMenu extends React.Component<NavMenuProps, NavMenuState> { 
    public constructor(props: NavMenuProps) {
        super(props);
        this.state = {
            value: props.location.pathname
        };
    }

    private handleSelection = (event: object, value: any) => {
        this.setState({
            value: value
        });
        this.props.history.push(value);
    }

    private actions: any[] = [{
        label: '',
        icon: <Router className={this.props.classes.navIcon} />,
        value: '/sensors'
    }, {
        label: '',
        icon: <People className={this.props.classes.navIcon} />,
        value: '/'
    }, {
        label: '',
        icon: <Settings className={this.props.classes.navIcon} />,
        value: '/settings'
    }]
    
    private renderPushNotificationsIcon() {
        switch (this.props.pushState.connectionStatus) {
            case PushConnectionStatus.CONNECTED:
                return <Notifications className={this.props.classes.navIcon}></Notifications>
            case PushConnectionStatus.CONNECTING:
                return <CircularProgress />
            case PushConnectionStatus.DISCONNECTED:
                return <NotificationsOff className={this.props.classes.navIcon}></NotificationsOff>
            case PushConnectionStatus.ERROR:
                return (
                    <NotificationImportant className={this.props.classes.navIcon}></NotificationImportant>
                )
        }
    }

    render() {
        let { classes } = this.props;

        return (
            <BottomNavigation showLabels={false} className={classes.root} onChange={this.handleSelection} value={this.state.value}>
                {this.actions.map((action, i) => {
                    return <BottomNavigationAction key={i} label={action.label} icon={action.icon} value={action.value} />
                })}
            </BottomNavigation>
        );
    }
}

const mapStateToProps = (state: any) => ({
    pushState: state.push
})


const mapDispatchToProps = (dispatch: Dispatch<Action<any>>) => ({
    dispatch: dispatch
})

export default withStyles(styles)(withRouter(connect(mapStateToProps, mapDispatchToProps)(NavMenu)));
