import { SensorConnectionState } from "./eventArgs/SensorStatusChanged";

export class SensorTypeMetadata {
    public constructor(public sensorTypeID: number,
                       public title: string,
                       public reliability: number) {}
}

export class SensorConnectionMetadata {
    public constructor(public sensorConnectionID: number,
                       public url: string,
                       public port: number,
                       public path: string,
                       public name: string,
                       public isActiveDefaultly: boolean,
                       public sensorTypeMetadata: SensorTypeMetadata) {}
}

export class SensorConnection {
    public constructor(
        public connectionMetadata: SensorConnectionMetadata, 
        public status: SensorConnectionState) {}
}