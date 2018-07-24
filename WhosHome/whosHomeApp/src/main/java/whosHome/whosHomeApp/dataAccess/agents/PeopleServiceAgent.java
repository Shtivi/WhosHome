package whosHome.whosHomeApp.dataAccess.agents;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import whosHome.common.SearchParams;
import whosHome.whosHomeApp.dataAccess.IPeopleDao;
import whosHome.whosHomeApp.models.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class PeopleServiceAgent implements IPeopleDao {
    private String _apiUrl;
    private String _peopleApiPath;
    private RestTemplate _restTemplate;

    public PeopleServiceAgent(String serviceUrl, String peopleApiPath) {
        _apiUrl = serviceUrl;
        _peopleApiPath = peopleApiPath;
        _restTemplate = new RestTemplate();
    }

    @Override
    public Collection<Person> fetchAllDetailed() {
        try {
            ResponseEntity<ArrayList<Person>> peopleResponse = _restTemplate.exchange(
                    _apiUrl + _peopleApiPath + "/detailed",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ArrayList<Person>>(){});
            return peopleResponse.getBody();
        } catch (HttpServerErrorException e) {
            throw new OperationFailedException(e.getResponseBodyAsString(), e);
        }
    }

    @Override
    public Collection<Person> search(String keyword) {
        try {
            ResponseEntity<ArrayList<Person>> peopleResponse = _restTemplate.exchange(
                    _apiUrl + _peopleApiPath + "/search/" + keyword,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ArrayList<Person>>(){});
            return peopleResponse.getBody();
        } catch (HttpServerErrorException e) {
            throw new OperationFailedException(e.getResponseBodyAsString(), e);
        }
    }

    @Override
    public Collection<Person> searchStrict(SearchParams params) {
        try {
            ResponseEntity<ArrayList<Person>> response = _restTemplate.exchange(
                    _apiUrl + _peopleApiPath + "/search",
                    HttpMethod.POST,
                    new HttpEntity<>(params.asMap()),
                    new ParameterizedTypeReference<ArrayList<Person>>(){});
            return response.getBody();
        } catch (HttpServerErrorException e) {
            throw new OperationFailedException(e.getResponseBodyAsString(), e);
        }
    }

    @Override
    public Collection<Person> fetchAll() {
        try {
            ResponseEntity<ArrayList<Person>> peopleResponse = _restTemplate.exchange(
                    _apiUrl + _peopleApiPath,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ArrayList<Person>>() {
                    });
            return peopleResponse.getBody();
        } catch (HttpServerErrorException e) {
            throw new OperationFailedException(e.getResponseBodyAsString(), e);
        }
    }

    @Override
    public Optional<Person> fetchById(String id) {
        try {
            Person result = _restTemplate.getForObject(_apiUrl + _peopleApiPath + "/" + id, Person.class);
            return Optional.ofNullable(result);
        } catch (HttpServerErrorException e) {
            throw new OperationFailedException(e.getResponseBodyAsString(), e);
        }
    }

    @Override
    public String add(Person record) {
        try {
            Person result = _restTemplate.postForObject(_apiUrl + _peopleApiPath, record, Person.class);
            return result.getID();
        } catch (HttpServerErrorException e) {
            throw new OperationFailedException(e.getResponseBodyAsString(), e);
        }
    }

    @Override
    public void add(Iterable<Person> records) {
        // TODO: 7/24/2018 Implement
        throw new NotImplementedException("need to add a new route in the people service");
    }

    @Override
    public void update(Person record) {
        try {
            _restTemplate.put(_apiUrl + _peopleApiPath, record);
        } catch (HttpServerErrorException e) {
            throw new OperationFailedException(e.getResponseBodyAsString(), e);
        }
    }

    @Override
    public void delete(String id) {
        try {
            _restTemplate.delete(_apiUrl + _peopleApiPath + "/" + id);
        } catch (HttpServerErrorException e) {
            throw new OperationFailedException(e.getResponseBodyAsString(), e);
        }
    }

    @Override
    public void delete(Collection<String> ids) {
        // TODO: 7/24/2018 Implement
        throw new NotImplementedException("need to add a new route in the people service");
    }
}
