package whosHome.common.sensors.client;


import whosHome.common.sensors.client.events.ActivityDetectionEventArgs;
import whosHome.common.sensors.client.events.ErrorEventArgs;
import whosHome.common.sensors.client.events.StatusChangeEventArgs;

public interface ISensorListener<T extends IdentificationData> {
    void onError(ErrorEventArgs args);
    void onStatusChange(StatusChangeEventArgs args);
    void onActivityDetection(ActivityDetectionEventArgs<T> args);
    void onEntitiesFetched(Iterable<T> entities);
}
