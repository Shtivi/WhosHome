import { SensorConnection } from "../models/SensorConnection";
import {Promise} from 'es6-promise'
import axios from 'axios'
import config from "../config/config";

export interface ISensorsAgent {
    fetchAllSensors(): Promise<SensorConnection[]>,
    toggleSensor(sensorID: number): Promise<void>
}

export class SensorAgent implements ISensorsAgent {
    public constructor(private sensorsApiConfig: any) {}

    public fetchAllSensors(): Promise<SensorConnection[]> {
        return new Promise<SensorConnection[]>((resolve, reject) => {
            axios.get(this.sensorsApiConfig.endpoint + this.sensorsApiConfig.api.fetchAllSensors)
                .then(res => resolve(<SensorConnection[]>res.data))
                .catch(reject);
        });
    }

    public toggleSensor(sensorID: number): Promise<void> {
        return new Promise<void>((resolve, reject) => {
            axios.get(`${this.sensorsApiConfig.endpoint}/${this.sensorsApiConfig.api.toggleSensor}/${sensorID}`)
                .then(res => resolve())
                .catch(error => reject(error));
        })
    }
}

export default new SensorAgent(config.sensors);