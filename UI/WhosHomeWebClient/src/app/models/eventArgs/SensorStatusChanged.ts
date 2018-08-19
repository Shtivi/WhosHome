import AbstractEventArgs from "./AbstractEventArgs";
import { SensorConnectionMetadata } from "../SensorConnection";

export enum SensorConnectionState {
    ERROR = 'Error',
    INITIALIZED = 'Initialized',
    CLOSED = 'Closed',
    CONNECTING = 'Connecting',
    CONNECTED = 'Connected'
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