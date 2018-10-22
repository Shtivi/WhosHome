import AbstractEventArgs from "./AbstractEventArgs";
import IdentificationData from "../entities/IdentificationData";
import { SensorConnectionMetadata } from "../SensorConnection";

export enum ActivityType { 
    IN = 'IN' , 
    OUT = 'OUT' 
}

export default class ActivityDetectionEventArgs<T extends IdentificationData> extends AbstractEventArgs {
    public eventType: ActivityType;
    public identificationData: T;
    public connectionMetadata: SensorConnectionMetadata;
    public time: Date;
}