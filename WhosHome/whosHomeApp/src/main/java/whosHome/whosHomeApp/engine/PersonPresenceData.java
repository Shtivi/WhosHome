package whosHome.whosHomeApp.engine;

import whosHome.common.sensors.client.events.ActivityDetectionEventArgs;
import whosHome.whosHomeApp.models.Person;

import java.time.Instant;
import java.util.*;

public class PersonPresenceData implements Iterable<ActivityDetectionEventArgs> {
    private Person _person;
    private List<ActivityDetectionEventArgs> _personActivities;
    private double _presenceCertainty;
    private Instant _lastUpdateTime;

    public PersonPresenceData(Person person, List<ActivityDetectionEventArgs> knownActivities) {
        _personActivities = knownActivities;
        _person = person;
        _presenceCertainty = calculatePresenceCertainty();
        _lastUpdateTime = Instant.now();
    }

    public PersonPresenceData(Person person, ActivityDetectionEventArgs firstActivity) {
        this(person, Arrays.asList(firstActivity));
    }

    public PersonPresenceData(Person person) {
        this(person, new ArrayList<>());
    }

    public static PersonPresenceData of(Person person) {
        return new PersonPresenceData(person);
    }

    public PersonPresenceData addActivity(ActivityDetectionEventArgs activityDetails) {
        this._personActivities.add(activityDetails);
        _lastUpdateTime = Instant.now();
        return this;
    }

    public PersonPresenceData deleteActivity(ActivityDetectionEventArgs activityDetails) {
        this._personActivities.remove(activityDetails);
        _lastUpdateTime = Instant.now();
        return this;
    }

    public double presenceCertainty() {
        return _presenceCertainty;
    }

    public Date lastUpdateTime() {
        return Date.from(_lastUpdateTime);
    }

    private double calculatePresenceCertainty() {
        return this._personActivities.stream()
                .mapToInt(activity -> {
                    int sensorTypeReliability = activity.getConnectionMetadata().getSensorTypeMetadata().getReliability();
                    if (activity.activityType() == ActivityDetectionEventArgs.Type.IN) {
                        return sensorTypeReliability;
                    } else {
                        return -sensorTypeReliability;
                    }
                })
                .average()
                .orElse(0);
    }

    @Override
    public Iterator<ActivityDetectionEventArgs> iterator() {
        return _personActivities.iterator();
    }
}
