package whosHome.whosHomeApp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.cli.Options;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.cli.*;
import whosHome.common.dataProviders.db.Hibernate;
import whosHome.common.dataProviders.db.SensorConnectionsMetadataDbDao;
import whosHome.common.sensors.client.ISensorConnection;
import whosHome.common.sensors.client.ISensorListener;
import whosHome.common.sensors.client.events.ActivityDetectionEventArgs;
import whosHome.common.sensors.client.events.ErrorEventArgs;
import whosHome.common.sensors.client.events.StatusChangeEventArgs;
import whosHome.whosHomeApp.dataAccess.IPeopleDao;
import whosHome.whosHomeApp.engine.sensors.builders.LanSensorConnectionBuilder;
import whosHome.whosHomeApp.models.Person;

import java.util.Collection;

@RestController
@SpringBootApplication
public class Application {
    public enum Environment { DEBUG, PROD }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("Application");
        logger.info("Starting initialization");

        CommandLine cli = null;
        try {
            cli = parseArgs(args);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }

        String env = cli.getOptionValue("environment");
        Config config = ConfigFactory.load(String.format("application.%s", env.toLowerCase()));

//        SpringApplication.run(Application.class, args);
        AppModule appModule = new AppModule(config.getConfig("whosHome"), Environment.valueOf(env.toUpperCase()));
        Injector injector = Guice.createInjector(appModule);
        IPeopleDao peopleService = injector.getInstance(IPeopleDao.class);
        Collection<Person> people = peopleService.search("ido");
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
//        Hibernate hibernate = new Hibernate("hibernate.cfg.xml");
//        SensorConnectionsMetadataDbDao dao = new SensorConnectionsMetadataDbDao(hibernate.getSessionFactory());
//        ISensorConnection connection = new LanSensorConnectionBuilder().apply(dao.fetchById(1).get());
//        connection.connect();
//        connection.listen(new ISensorListener() {
//            @Override
//            public void onError(ErrorEventArgs args) {
//
//            }
//
//            @Override
//            public void onStatusChange(StatusChangeEventArgs args) {
//
//            }
//
//            @Override
//            public void onActivityDetection(ActivityDetectionEventArgs args) {
//                System.out.println("2");
//            }
//
//            @Override
//            public void onEntitiesFetched(Iterable entities) {
//                System.out.println("1");
//            }
//        });
    }

    private static CommandLine parseArgs(String[] args) throws ParseException {
        Option environmentOption = Option.builder()
                .longOpt("environment")
                .desc("Set the environment configuration [prod / debug]")
                .hasArg()
                .required()
                .build();

        Options cliOptions = new Options().addOption(environmentOption);
        DefaultParser cliParser = new DefaultParser();
        CommandLine cli = cliParser.parse(cliOptions, args);
        return cli;
    }

    @RequestMapping(value = "/isAlive", method = RequestMethod.GET)
    public boolean greeting() {
        return true;
    }
}