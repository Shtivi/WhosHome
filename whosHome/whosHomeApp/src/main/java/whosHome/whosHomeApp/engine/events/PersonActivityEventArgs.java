package whosHome.whosHomeApp.engine.events;

import whosHome.common.sensors.client.events.ActivityDetectionEventArgs;
import whosHome.whosHomeApp.models.Person;

public class PersonActivityEventArgs {
    private ActivityDetectionEventArgs _activityDetails;
    private Person _subject;
    private double _presenceChances;


    public PersonActivityEventArgs(ActivityDetectionEventArgs activityDetails, Person subject, double presenceChances) {
        _activityDetails = activityDetails;
        _subject = subject;
        _presenceChances = presenceChances;
    }

    public ActivityDetectionEventArgs getActivityDetails() {
        return _activityDetails;
    }

    public Person getSubject() {
        return _subject;
    }

    public double getPresenceChances() {
        return _presenceChances;
    }

    private boolean isRecognized() {
        return _subject != null;
    }
}
