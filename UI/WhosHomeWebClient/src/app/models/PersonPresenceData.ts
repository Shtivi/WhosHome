import Person from "./Person";
import ActivityDetectionEventArgs from "./eventArgs/ActivityDetectionEventArgs";

export default class PersonPresenceData {
    public person: Person;
    public personActivities: ActivityDetectionEventArgs<any>[];
    public presenceChances: number;
    public lastUpdateTime: Date;
}