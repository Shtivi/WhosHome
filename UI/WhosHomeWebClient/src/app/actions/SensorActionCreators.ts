import { ActionTypes, AsyncStatus } from "./ActionTypes";
import SensorsAgent from '../services/SensorsAgent'
import { SensorConnection } from "../models/SensorConnection";
import { DeferredAction, DeferredActionBuilder } from "./DeferredAction";

export const toggleSensor = (sensorID: number): DeferredAction<void, number> => {
    return DeferredActionBuilder
        .of<void, number>(ActionTypes.TOGGLE_SENSOR)
        .withExtraArg(sensorID)
        .withPromise(SensorsAgent.toggleSensor(sensorID))
        .build();
}

export const fetchAllSensors = (): DeferredAction<SensorConnection[]> => {
    return DeferredActionBuilder
        .of<SensorConnection[]>(ActionTypes.FETCH_SENSORS)
        .withPromise(SensorsAgent.fetchAllSensors())
        .build();
}