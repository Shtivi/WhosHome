package whosHome.whosHomeApp.dataAccess;

import whosHome.common.SearchParams;
import whosHome.common.dataProviders.IDataProvider;
import whosHome.whosHomeApp.models.Person;

import java.util.Collection;

public interface IPeopleDao extends IDataProvider<String, Person> {
    /**
     * Fetches all the people records, with all the data related to them.
     * Notice that fetchAll() will fetch minfied records, and not the whole data.
     * @return a collection contains the people objects fetched
     */
    Collection<Person> fetchAllDetailed();

    /**
     * Searches people with any field value matches the keyword received.
     * @param keyword A string to search people according to.
     * @return A collection of people records matching the search keyword.
     */
    Collection<Person> search(String keyword);

    /**
     * Searches for people with field values exactly equal to all the search params fields.
     * @param params A search params object contains the values to search for.
     * @return A collection of people matching the given search params value.
     */
    Collection<Person> searchStrict(SearchParams params);
}
