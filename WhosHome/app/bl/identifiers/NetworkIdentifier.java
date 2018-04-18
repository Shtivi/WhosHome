package bl.identifiers;

import bl.dataAccessors.PeopleAccessor;
import bl.sensors.lan.NetworkIdentificationData;
import models.Person;

public class NetworkIdentifier implements Identifier<NetworkIdentificationData> {

	@Override
	public Person identify(NetworkIdentificationData identificationData) {
		try {
			Person[] results = 
					PeopleAccessor.instance().search(
							PeopleAccessor
							.instance()
							.createSearchParams()
							.setMacAddress(identificationData.getMac())); 
			
			if (results == null || results.length == 0) {
				return null;
			} else {
				return results[0];
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
