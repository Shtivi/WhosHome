package whosHome.whosHomeApp.engine.recognition;

import com.google.inject.Inject;
import whosHome.common.sensors.client.IdentificationData;
import whosHome.whosHomeApp.dataAccess.IDevicesDao;
import whosHome.whosHomeApp.dataAccess.IPeopleDao;
import whosHome.whosHomeApp.models.Device;

import java.util.Optional;

public class DevicesRecognizer implements IRecognizer {
    private IPeopleDao _peopleDao;
    private IDevicesDao _devicesDao;

    @Inject
    public DevicesRecognizer(IDevicesDao devicesDao) {
        _devicesDao = devicesDao;
    }

    @Override
    public Optional<String> recognizePersonID(IdentificationData identificationData) {
        String macAddress = identificationData.getIdentificationData();
        Optional<Device> device = _devicesDao.fetchById(macAddress);
        if (device.isPresent()) {
            return Optional.of(device.get().getOwnerID());
        }
        return Optional.empty();
    }
}