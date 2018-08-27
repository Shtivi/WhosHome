import { ActionTypes, AsyncStatus } from "./ActionTypes";
import SensorsAgent from '../services/SensorsAgent'
import { SensorConnection } from "../models/SensorConnection";
import { DeferredAction, DeferredActionBuilder } from "./DeferredAction";

export const toggleSensor = (sensorID: number) => {
    // return (dispatch: Dispatch) => {
    //     dispatch(togglingSensor(sensorID));
    //     SensorsAgent.toggleSensor(sensorID)
    //         .then(() => dispatch(finishedTogglingSensor(sensorID)))
    //         .catch((err: any) => errorTogglingSensor(sensorID, err));
    // }
}

export const fetchAllSensors = (): DeferredAction<SensorConnection[]> => {
    return DeferredActionBuilder
        .of<SensorConnection[]>(ActionTypes.FETCH_SENSORS)
        .withPromise(SensorsAgent.fetchAllSensors())
        .build();
}