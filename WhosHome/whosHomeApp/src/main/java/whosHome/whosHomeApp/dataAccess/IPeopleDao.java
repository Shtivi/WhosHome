package whosHome.whosHomeApp.dataAccess;

import whosHome.common.SearchParams;
import whosHome.common.dataProviders.IDataProvider;
import whosHome.whosHomeApp.models.Person;

import java.util.Collection;

public interface IPeopleDao extends IDataProvider<String, Person> {
    Collection<Person> fetchAllDetailed();
    Collection<Person> search(String keyword);
    Collection<Person> search(SearchParams params);
}
