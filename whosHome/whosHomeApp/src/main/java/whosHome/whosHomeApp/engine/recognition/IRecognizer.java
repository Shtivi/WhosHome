package whosHome.whosHomeApp.engine.recognition;

import whosHome.common.sensors.client.IdentificationData;
import whosHome.whosHomeApp.models.Person;

import java.util.Map;
import java.util.Optional;

public interface IRecognizer {
    Optional<String> recognizePersonID(IdentificationData identificationData);
    // TODO: 8/7/2018 Add batching recgnition
}
