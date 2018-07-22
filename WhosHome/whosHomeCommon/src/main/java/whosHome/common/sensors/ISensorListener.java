package whosHome.common.sensors;


import whosHome.common.sensors.events.ActivityDetectionEventArgs;
import whosHome.common.sensors.events.ErrorEventArgs;
import whosHome.common.sensors.events.StatusChangeEventArgs;

public interface ISensorListener<T extends IdentificationData> {
    void onError(ErrorEventArgs args);
    void onStatusChange(StatusChangeEventArgs args);
    void onActivityDetection(ActivityDetectionEventArgs<T> args);
    void onEntitiesFetched(Iterable<T> entities);
}
