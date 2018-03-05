package bl.identifiers;

import models.Person;

public interface Identifier<T extends IdentificationData> {
	Person identify(T identificationData);
}
