package whosHome.whosHomeApp.engine;

import com.fasterxml.jackson.annotation.JsonProperty;
import whosHome.common.sensors.client.events.ActivityDetectionEventArgs;
import whosHome.whosHomeApp.models.Person;

import java.time.Instant;
import java.util.*;

public class PersonPresenceData implements Iterable<ActivityDetectionEventArgs> {
    public static PersonPresenceData of(Person person) {
        return new PersonPresenceData(person);
    }

    private Person _person;
    private List<ActivityDetectionEventArgs> _personActivities;
    private double _presenceChances;
    private Instant _lastUpdateTime;

    public PersonPresenceData(Person person, List<ActivityDetectionEventArgs> knownActivities) {
        _personActivities = knownActivities;
        _person = person;
        _presenceChances = calculatePresenceChances();
        _lastUpdateTime = Instant.now();
    }

    public PersonPresenceData(Person person, ActivityDetectionEventArgs firstActivity) {
        this(person, Arrays.asList(firstActivity));
    }

    public PersonPresenceData(Person person) {
        this(person, new ArrayList<>());
    }

    public PersonPresenceData addActivity(ActivityDetectionEventArgs activityDetails) {
        this._personActivities.add(activityDetails);
        _presenceChances = calculatePresenceChances();
        _lastUpdateTime = Instant.now();
        return this;
    }

    public PersonPresenceData deleteActivity(ActivityDetectionEventArgs activityDetails) {
        this._personActivities.remove(activityDetails);
        _presenceChances = calculatePresenceChances();
        _lastUpdateTime = Instant.now();
        return this;
    }

    /**
     * Calculates what are the chances that the subject is currently present, according to the activities supplied.
     * Each activity refers to the sensor detected it, and each sensor type has it's reliability factor.
     * @return The chances that the subject is currently present, as a number between 0 and 10.
     */
    @JsonProperty
    public double presenceChances() {
        return _presenceChances;
    }

    @JsonProperty
    public Date lastUpdateTime() {
        return Date.from(_lastUpdateTime);
    }

    @JsonProperty(value = "personActivities")
    @Override
    public Iterator<ActivityDetectionEventArgs> iterator() {
        return _personActivities.iterator();
    }

    @JsonProperty
    public Person getPerson() {
        return this._person;
    }

    private double calculatePresenceChances() {
        return this._personActivities.stream()
                .mapToDouble(activity -> {
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
}
