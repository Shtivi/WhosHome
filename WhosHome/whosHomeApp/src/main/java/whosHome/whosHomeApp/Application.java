package whosHome.whosHomeApp;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import whosHome.common.dataProviders.db.Hibernate;
import whosHome.common.dataProviders.db.SensorConnectionsMetadataDbDao;
import whosHome.common.sensors.ISensorConnection;
import whosHome.whosHomeApp.dataAccess.PeopleServiceAgent;
import whosHome.whosHomeApp.engine.sensors.ISensorConnectionsFactory;
import whosHome.whosHomeApp.engine.sensors.SensorConnectionsFactory;
import whosHome.whosHomeApp.models.Person;

import java.util.Collection;

@RestController
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        Config config = ConfigFactory.load("application");
        Hibernate hibernate = new Hibernate("hibernate.cfg.xml");

        SensorConnectionsMetadataDbDao dao = new SensorConnectionsMetadataDbDao(hibernate.getSessionFactory());
        ISensorConnectionsFactory factory = new SensorConnectionsFactory(dao);
        ISensorConnection connection = factory.createAllConnectionS().get(0);
        PeopleServiceAgent agent = new PeopleServiceAgent(config.getString("whosHome.peopleService.url"));
        Collection<Person> people = agent.fetchAll();
    }

    @RequestMapping(value = "/isAlive", method = RequestMethod.GET)
    public boolean greeting() {
        return true;
    }
}