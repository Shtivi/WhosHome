package whosHome.whosHomeApp.engine.recognition;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import sensorclient.entities.LanEntity;
import whosHome.common.sensors.client.IdentificationData;
import whosHome.whosHomeApp.dataAccess.IDevicesDao;
import whosHome.whosHomeApp.dataAccess.IPeopleDao;
import whosHome.whosHomeApp.models.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PeopleRecognitionManager {
    private Map<Class, IRecognizer> _recognizers;
    IPeopleDao _peopleDao;

    @Inject
    public PeopleRecognitionManager(IPeopleDao peopleDao, IDevicesDao devicesDao) {
        _peopleDao = peopleDao;
        _recognizers = new HashMap<>();
        _recognizers.put(LanEntity.class, new DevicesRecognizer(devicesDao));
    }

    public Optional<Person> recognize(IdentificationData identificationData) {
        IRecognizer recognizer = _recognizers.get(identificationData.getClass());
        Optional<String> personID = recognizer.recognizePersonID(identificationData);
        if (personID.isPresent()) {
            Optional<Person> searchResult = _peopleDao.fetchById(personID.get());
            return searchResult;
        }
        return Optional.empty();
    }
}
