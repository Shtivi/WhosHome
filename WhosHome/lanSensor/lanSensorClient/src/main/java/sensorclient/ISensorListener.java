package sensorclient;

import sensorclient.entities.LanEntity;
import sensorclient.events.DeviceConnectionEventArgs;
import sensorclient.events.ErrorEventArgs;
import sensorclient.events.StatusChangeEventArgs;

public interface ISensorListener {
    void onError(ErrorEventArgs args);
    void onStatusChange(StatusChangeEventArgs args);
    void onDeviceEvent(DeviceConnectionEventArgs args);
    void allEntitiesFetched(Iterable<LanEntity> entities);
}
