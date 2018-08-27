import * as React from "react";
import { withStyles, createStyles, WithStyles, Theme } from '@material-ui/core/styles';
import { connect } from 'react-redux'
import { Dispatch, Action } from 'redux'
import { SensorsState } from "../store/Store";
import Button from '@material-ui/core/Button';
import CircularProgress from '@material-ui/core/CircularProgress';
import List from '@material-ui/core/List';
import { fetchAllSensors } from "../actions/SensorActionCreators";

const styles = (theme: Theme) => createStyles({
    root: {
        marginTop: '75px',
        textAlign: 'center',
    },
    statsWrapper: {
        position: 'relative',
    },
    statsContent: {
        backgroundColor: '#fff',
        height: '100px',
        width: '100px',
        boxShadow: 'none',
        color: '#555',
        fontSize: '42px',
        fontWeight: 'bold',
    },
    statsProgs: {
        position: 'absolute',
        top: '50%',
        left: '50%',
        marginTop: -50,
        marginLeft: -50,
    },
    sensorsList: {
        marginTop: '5%',
        paddingBottom: '6%'
    }
})

interface SensorsPageProps extends WithStyles<typeof styles> {
    dispatch: Dispatch<Action>,
    sensorsState: SensorsState
}

interface SensorPageState {}

const mapStateToProps = (state: any) => ({
    sensorsState: state.sensors
})

const mapDispatchToProps = (dispatch: Dispatch<Action<any>>) => ({
    dispatch: dispatch
})

class SensorsPage extends React.Component<SensorsPageProps, SensorPageState> {
    public constructor(props: SensorsPageProps) {
        super(props);
        this.props.dispatch(fetchAllSensors());
    }

    render() {
        const {classes} = this.props;
        return (
            <div className={classes.root}>
                <div className={classes.statsWrapper}>
                    <Button variant="fab" className={classes.statsContent}>
                        4
                        <span style={{fontSize: '16px'}}>/5</span>
                    </Button>
                    <CircularProgress variant="static" value={50} size={100} className={classes.statsProgs} />
                </div>
                <List className={classes.sensorsList}>
                    {/* {[1,2,3,4,5,6,7,8,9,10,11].map((sensor, i) => (
                    ))}; */}
                </List>
            </div>
        );
    }
}

export default withStyles(styles)(connect(mapStateToProps, mapDispatchToProps)(SensorsPage))