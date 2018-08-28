import AbstractEventArgs from "./AbstractEventArgs";
import { SensorConnectionMetadata } from "../SensorConnection";

export enum SensorConnectionState {
    ERROR = 'ERROR',
    INITIALIZED = 'INITIALIZED',
    CLOSED = 'CLOSED',
    CONNECTING = 'CONNECTING',
    CONNECTED = 'CONNECTED'
}

export default class SensorStatusChanged extends AbstractEventArgs {
    public oldStatus: SensorConnectionState;
    public newStatus: SensorConnectionState;
    public reason: string;
    public sensorConnectionMetadata: SensorConnectionMetadata;

    public constructor() {
        super();
    }
}