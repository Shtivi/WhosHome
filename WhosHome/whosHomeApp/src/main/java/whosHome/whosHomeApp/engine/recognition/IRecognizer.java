package whosHome.whosHomeApp.engine.recognition;

import whosHome.common.sensors.client.IdentificationData;
import whosHome.whosHomeApp.models.Person;

import java.util.Optional;

public interface IRecognizer {
    Optional<String> recognizePersonID(IdentificationData identificationData);
}
