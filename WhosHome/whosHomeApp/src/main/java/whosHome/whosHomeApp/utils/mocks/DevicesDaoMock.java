package whosHome.whosHomeApp.utils.mocks;

import whosHome.whosHomeApp.dataAccess.IDevicesDao;
import whosHome.whosHomeApp.models.Device;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class DevicesDaoMock implements IDevicesDao {
    private ArrayList<Device> devices;

    public DevicesDaoMock() {
        devices = new ArrayList<>();

        Device idosLaptop = new Device();
        idosLaptop.setMacAddress("f8-94-c2-92-ff-e0");
        idosLaptop.setOwnerID("5ad70876cda214993da785b4");
        devices.add(idosLaptop);
    }

    @Override
    public Collection<Device> fetchByOwnerID(String ownerID) {
        return devices;
    }

    @Override
    public Collection<Device> fetchAll() {
        return devices;
    }

    @Override
    public Optional<Device> fetchById(String id) {
        return Optional.of(devices.get(0));
    }

    @Override
    public String add(Device record) {
        return null;
    }

    @Override
    public void add(Iterable<Device> records) {

    }

    @Override
    public void update(Device record) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void delete(Collection<String> ids) {

    }
}
