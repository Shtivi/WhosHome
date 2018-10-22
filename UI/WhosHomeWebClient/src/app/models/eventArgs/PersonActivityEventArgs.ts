import AbstractEventArgs from "./AbstractEventArgs";
import ActivityDetectionEventArgs from "./ActivityDetectionEventArgs";
import Person from "../Person";

export default class PersonActivityEventArgs extends AbstractEventArgs {
    public activityDetails: ActivityDetectionEventArgs<any>;
    public subject: Person;
    public presenceChances: number;
}