import AbstractEventArgs from "./AbstractEventArgs";
import { SensorConnectionMetadata } from "../SensorConnection";

export default class SensorError extends AbstractEventArgs {
    public error: string;
    public connectionMetadata: SensorConnectionMetadata;

    public constructor() {
        super();
    }
}