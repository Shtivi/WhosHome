import * as React from "react";
import { PushConnectionStatus } from "../models/PushConnectionStatus";
import { Dispatch } from 'redux';
import { Action } from 'redux';
import { PushNotificationsState } from "../store/Store";
import { connect } from "react-redux";
import { subscribePushNotifications } from "../actions/PushNotificationsActionCreators";

interface PushNotificationsProps {
    pushStatus: PushNotificationsState
    dispatch: Dispatch<Action>
}

class PushNotifications extends React.Component<PushNotificationsProps> {
    render() {
        return (
            <div>
                <button onClick={() => this.props.dispatch(subscribePushNotifications())}>subscribePushNotifications</button>
                {this.props.pushStatus.connectionStatus.toString()}
                {this.props.pushStatus.error ? this.props.pushStatus.error : "" }
            </div>
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