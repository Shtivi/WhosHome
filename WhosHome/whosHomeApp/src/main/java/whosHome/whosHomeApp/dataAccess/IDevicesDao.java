package whosHome.whosHomeApp.dataAccess;

import whosHome.common.dataProviders.IDataProvider;
import whosHome.whosHomeApp.models.Device;

import java.util.Collection;

public interface IDevicesDao extends IDataProvider<String, Device> {
    Collection<Device> fetchByOwnerID(String ownerID);
    Device fetchByMacAddress(String macAddress);
}
