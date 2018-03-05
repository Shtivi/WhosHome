package bl.identifiers;

import bl.sensors.lan.NetworkIdentificationData;
import models.Person;

public class NetworkIdentifier implements Identifier<NetworkIdentificationData> {

	@Override
	public Person identify(NetworkIdentificationData identificationData) {
		return null;
	}

}
