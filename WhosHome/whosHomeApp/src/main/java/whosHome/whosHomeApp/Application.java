package whosHome.whosHomeApp;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import whosHome.common.dataProviders.db.Hibernate;
import whosHome.common.dataProviders.db.SensorConnectionsMetadataDbDao;
import whosHome.common.sensors.client.ISensorConnection;
import whosHome.common.sensors.client.ISensorListener;
import whosHome.common.sensors.client.events.ActivityDetectionEventArgs;
import whosHome.common.sensors.client.events.ErrorEventArgs;
import whosHome.common.sensors.client.events.StatusChangeEventArgs;
import whosHome.whosHomeApp.engine.sensors.builders.LanSensorConnectionBuilder;

@RestController
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        Config config = ConfigFactory.load("application");

//        SensorTypeMetadata sensorTypeMetadata = new SensorTypeMetadata();
//        sensorTypeMetadata.setReliability(10);
//        sensorTypeMetadata.setSensorTypeID(1);
//        sensorTypeMetadata.setTitle("lan sensor");
//
//        SensorConnectionMetadata sensorConnectionMetadata = new SensorConnectionMetadata();
//        sensorConnectionMetadata.setActiveDefaultly(true);
//        sensorConnectionMetadata.setName("lan");
//        sensorConnectionMetadata.setPort(6000);;
//        sensorConnectionMetadata.setSensorConnectionID(1);
//        sensorConnectionMetadata.setUrl("ws://localhost");
//        sensorConnectionMetadata.setSensorTypeMetadata(sensorTypeMetadata);
        Hibernate hibernate = new Hibernate("hibernate.cfg.xml");
        SensorConnectionsMetadataDbDao dao = new SensorConnectionsMetadataDbDao(hibernate.getSessionFactory());
        ISensorConnection connection = new LanSensorConnectionBuilder().apply(dao.fetchById(1).get());
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
                System.out.println("2");
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