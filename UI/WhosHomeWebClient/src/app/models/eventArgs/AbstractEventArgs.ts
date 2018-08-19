export default abstract class AbstractEventArgs {
    public time: Date;

    public constructor(time?: Date) {
        this.time = time != null ? time : new Date();
    }
}