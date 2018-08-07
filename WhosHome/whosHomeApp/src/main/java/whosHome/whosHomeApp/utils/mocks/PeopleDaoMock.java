package whosHome.whosHomeApp.utils.mocks;

import whosHome.common.SearchParams;
import whosHome.whosHomeApp.dataAccess.IPeopleDao;
import whosHome.whosHomeApp.models.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PeopleDaoMock implements IPeopleDao {
    private List<Person> _people;

    public PeopleDaoMock() {
        _people = new ArrayList<>();
        _people.add(new Person.Builder("Ido", "Shtivi").withID("a").build());
    }

    @Override
    public Collection<Person> fetchAllDetailed() {
        return _people;
    }

    @Override
    public Collection<Person> search(String keyword) {
        return _people;
    }

    @Override
    public Collection<Person> searchStrict(SearchParams params) {
        return _people;
    }

    @Override
    public Collection<Person> fetchAll() {
        return _people;
    }

    @Override
    public Optional<Person> fetchById(String id) {
        return Optional.of(_people.get(0));
    }

    @Override
    public String add(Person record) {
        return null;
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
