import * as React from 'react';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Avatar from '@material-ui/core/Avatar';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import IconButton from '@material-ui/core/IconButton';
import SvgIcon from '@material-ui/core/SvgIcon';
import EditIcon from '@material-ui/icons/Edit';
import PowerIcon from '@material-ui/icons/Power';
import WifiIcon from '@material-ui/icons/Wifi';
import BluetoothIcon from '@material-ui/icons/Bluetooth';
import UnknownIcon from '@material-ui/icons/DeviceUnknown';
import { withStyles, createStyles, WithStyles, Theme } from '@material-ui/core/styles';
import { SensorConnection } from '../models/SensorConnection';
import blue from '@material-ui/core/colors/blue';
import green from '@material-ui/core/colors/green';
import red from '@material-ui/core/colors/red';
import { SensorConnectionState } from '../models/eventArgs/SensorStatusChanged';
import { CircularProgress } from '@material-ui/core';

const styles = (theme: Theme) => createStyles({
    root: {

    },
    sensorTypeIcon: {
        fontSize: '40px'
    },
});

interface SensorsListItemPros extends WithStyles<typeof styles> {
    sensorConnection: SensorConnection,
    sensorToggleHandler: (sensorID: number) => void
}

class SensorListItem extends React.Component<SensorsListItemPros> {
    private defineStatusColor(): string {
        switch(this.props.sensorConnection.status) {
            case SensorConnectionState.CONNECTED:
                return green[600];
            case SensorConnectionState.CONNECTING: 
                return blue[600];
            case SensorConnectionState.INITIALIZED:
                return blue[600];
            case SensorConnectionState.CLOSED: 
                return blue[600];
            case SensorConnectionState.ERROR:
                return red[600];
            default:
                return red[600];
        }
    }
    
    private renderSensorTypeIcon() {
        const {sensorConnection} = this.props;
        const statusColor: string = this.defineStatusColor();

        switch (sensorConnection.connectionMetadata.sensorTypeMetadata.sensorTypeID) {
            case 1:
                return <WifiIcon style={{color: statusColor}} />
            default:
                return <UnknownIcon style={{color: statusColor}} />
        }
    }

    render() {
        const {sensorConnection, classes} = this.props;
        return (
            <ListItem>
                <ListItemIcon className={classes.sensorTypeIcon}>
                    {this.renderSensorTypeIcon()}
                </ListItemIcon>
                <ListItemText 
                    primary={sensorConnection.connectionMetadata.name} 
                    secondary={`${sensorConnection.connectionMetadata.sensorTypeMetadata.title}, ${sensorConnection.status}`} 
                />
                <ListItemSecondaryAction>
                    {(sensorConnection.status == SensorConnectionState.CONNECTING) ? (
                        <CircularProgress />
                    ) : (
                        <IconButton onClick={() => this.props.sensorToggleHandler(sensorConnection.connectionMetadata.sensorConnectionID)}>
                            <SvgIcon><PowerIcon /></SvgIcon>
                        </IconButton>
                    )}
                </ListItemSecondaryAction>
            </ListItem>
        );
    }
}

export default withStyles(styles)(SensorListItem);