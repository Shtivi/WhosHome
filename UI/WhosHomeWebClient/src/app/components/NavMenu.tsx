import * as React from "react";
import BottomNavigation from '@material-ui/core/BottomNavigation';
import BottomNavigationAction from '@material-ui/core/BottomNavigationAction';
import { withStyles, createStyles, WithStyles } from '@material-ui/core/styles';
import Router from '@material-ui/icons/Router';
import People from '@material-ui/icons/People';
import Settings from '@material-ui/icons/Settings';
import { NavLink, Link, RouteComponentProps, withRouter } from 'react-router-dom'

const styles = createStyles({
    root: {
        backgroundColor: '#f0f0f0',
        borderTop: '1px solid #e6e6e6',
        position: 'fixed',
        bottom: '0px',
        width: '100%'
    }
});

interface NavMenuProps extends WithStyles<typeof styles>, RouteComponentProps<any> {
}

interface NavMenuState {
    value: any
}

class NavMenu extends React.Component<NavMenuProps, NavMenuState> { 
    public constructor(props: NavMenuProps) {
        super(props);
        this.state = {
            value: 0
        };
    }

    private handleSelection = (event: object, value: any) => {
        this.setState({
            value: value
        });
        this.props.history.push(value);
    }
    
    render() {
        let { classes } = this.props;

        return (
            <BottomNavigation showLabels className={classes.root} onChange={this.handleSelection} value={this.state.value}>
                <BottomNavigationAction label='Sensors' icon={<Router />} value='/sensors' />
                <BottomNavigationAction label="Who's Home?" icon={<People />} value='/' />
                <BottomNavigationAction label="Settings" icon={<Settings />} value='/settings' />
            </BottomNavigation>
        );
    }
}

export default withStyles(styles)(withRouter(NavMenu));