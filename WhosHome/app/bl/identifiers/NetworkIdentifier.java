package bl.identifiers;

import bl.dataAccessors.PeopleAccessor;
import bl.sensors.lan.NetworkIdentificationData;
import models.Person;

public class NetworkIdentifier implements Identifier<NetworkIdentificationData> {

	@Override
	public Person identify(NetworkIdentificationData identificationData) {
		try {
			return PeopleAccessor.instance().getByID("5a9f28e6ffb702e7786254b8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
