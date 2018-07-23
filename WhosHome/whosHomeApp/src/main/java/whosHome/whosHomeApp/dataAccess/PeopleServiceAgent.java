package whosHome.whosHomeApp.dataAccess;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import whosHome.common.SearchParams;
import whosHome.whosHomeApp.models.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class PeopleServiceAgent implements IPeopleDao {
    private String _apiUrl;
    private RestTemplate _restTemplate;

    public PeopleServiceAgent(String peopleServiceApiUrl) {
        _apiUrl = peopleServiceApiUrl;
        _restTemplate = new RestTemplate();
    }

    @Override
    public Collection<Person> fetchAllDetailed() {
        ResponseEntity<ArrayList<Person>> exchange = _restTemplate.exchange(
                _apiUrl + "/detailed",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<Person>>() {});
        return exchange.getBody();
    }

    @Override
    public Collection<Person> search(String keyword) {
        return null;
    }

    @Override
    public Collection<Person> search(SearchParams params) {
        return null;
    }

    @Override
    public Collection<Person> fetchAll() {
        ResponseEntity<ArrayList<Person>> exchange = _restTemplate.exchange(
                _apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<Person>>() {});
        return exchange.getBody();
    }

    @Override
    public Optional<Person> fetchById(String id) {
        return Optional.empty();
    }

    @Override
    public void add(Person record) {

    }

    @Override
    public void add(Iterable<Person> records) {

    }

    @Override
    public void update(Person record) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void delete(Collection<String> ids) {

    }
}
