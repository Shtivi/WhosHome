import AbstractEventArgs from "./AbstractEventArgs";

export enum EngineStatus {
    CREATED = 'Created',
    INITIALIZED = 'Initialized',
    WORKING = 'Working'
}

export default class EngineStatusChanged extends AbstractEventArgs {
    public oldStatus: EngineStatus;
    public newStatus: EngineStatus;
    public reason: string;

    public constructor() {
        super();
    }
}