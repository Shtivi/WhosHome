import * as React from "react";
import BottomNavigation from '@material-ui/core/BottomNavigation';
import BottomNavigationAction from '@material-ui/core/BottomNavigationAction';
import { withStyles, createStyles, WithStyles } from '@material-ui/core/styles';
import Router from '@material-ui/icons/RouterOutlined';
import People from '@material-ui/icons/PeopleOutlined';
import Settings from '@material-ui/icons/SettingsOutlined';
import { NavLink, Link, RouteComponentProps, withRouter } from 'react-router-dom'

const styles = createStyles({
    root: {
        backgroundColor: '#f5f5f5',
        borderTop: '1px solid #e9e9e9',
        position: 'fixed',
        bottom: '0px',
        width: '100%'
    }, 
    navIcon: {
        fontSize: '36px'
    }
});

interface NavMenuProps extends WithStyles<typeof styles>, RouteComponentProps<any> {}

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
    
    render() {
        let { classes } = this.props;

        return (
            <BottomNavigation showLabels={false} className={classes.root} onChange={this.handleSelection} value={this.state.value}>
                {/* <BottomNavigationAction label='' icon={<Router className={classes.navIcon} />} value='/sensors' />
                <BottomNavigationAction label="" icon={<People className={classes.navIcon} />} value='/' />
                <BottomNavigationAction label='' icon={<Settings className={classes.navIcon} />} value='/settings' /> */}
                {this.actions.map((action, i) => {
                    return <BottomNavigationAction key={i} label={action.label} icon={action.icon} value={action.value} />
                })}
            </BottomNavigation>
        );
    }
}

export default withStyles(styles)(withRouter(NavMenu));