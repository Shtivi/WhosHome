package whosHome.whosHomeApp;

import com.typesafe.config.ConfigFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import whosHome.common.dataProviders.db.Hibernate;
import whosHome.common.dataProviders.db.SensorConnectionsMetadataDbDao;
import whosHome.common.models.SensorConnectionMetadata;
import whosHome.common.sensors.ISensorConnection;
import whosHome.common.sensors.ISensorListener;
import whosHome.common.sensors.events.ActivityDetectionEventArgs;
import whosHome.common.sensors.events.ErrorEventArgs;
import whosHome.common.sensors.events.StatusChangeEventArgs;
import whosHome.whosHomeApp.engine.sensors.ISensorConnectionsFactory;
import whosHome.whosHomeApp.engine.sensors.SensorConnectionsFactory;

@RestController
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        ConfigFactory.load("application");
        Hibernate hibernate = new Hibernate("hibernate.cfg.xml");

        SensorConnectionsMetadataDbDao dao = new SensorConnectionsMetadataDbDao(hibernate.getSessionFactory());
        ISensorConnectionsFactory factory = new SensorConnectionsFactory(dao);
        ISensorConnection connection = factory.createAllConnectionS().get(0);
        connection.connect();
        connection.listen(new ISensorListener() {
            @Override
            public void onError(ErrorEventArgs args) {

            }

            @Override
            public void onStatusChange(StatusChangeEventArgs args) {

            }

            @Override
            public void onActivityDetection(ActivityDetectionEventArgs args) {

            }

            @Override
            public void onEntitiesFetched(Iterable entities) {
                System.out.println("1");
            }
        });
    }

    @RequestMapping(value = "/isAlive", method = RequestMethod.GET)
    public boolean greeting() {
        return true;
    }
}