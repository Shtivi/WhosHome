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
import whosHome.common.dataProviders.ISensorConnectionsMetadataDao;
import whosHome.common.dataProviders.ISensorTypesMetadataDao;
import whosHome.common.dataProviders.db.Hibernate;
import whosHome.common.dataProviders.db.SensorConnectionsMetadataDbDao;
import whosHome.common.models.SensorConnectionMetadata;
import whosHome.common.models.SensorTypeMetadata;
import whosHome.common.sensors.client.ISensorConnection;
import whosHome.common.sensors.client.ISensorListener;
import whosHome.common.sensors.client.events.ActivityDetectionEventArgs;
import whosHome.common.sensors.client.events.ErrorEventArgs;
import whosHome.common.sensors.client.events.StatusChangeEventArgs;
import whosHome.whosHomeApp.dataAccess.IPeopleDao;
import whosHome.whosHomeApp.engine.recognition.PeopleRecognitionManager;
import whosHome.whosHomeApp.engine.sensors.ISensorConnectionsFactory;
import whosHome.whosHomeApp.engine.sensors.builders.LanSensorConnectionBuilder;
import whosHome.whosHomeApp.models.Person;

import java.util.Collection;
import java.util.Optional;

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
        ISensorConnectionsFactory connectionsFactory = injector.getInstance(ISensorConnectionsFactory.class);
        PeopleRecognitionManager recognizer = injector.getInstance(PeopleRecognitionManager.class);
        ISensorConnection connection = connectionsFactory.createConnection(1);
        connection.connect();
        connection.listen(new ISensorListener() {
            @Override
            public void onError(ErrorEventArgs args) {
                System.out.println(args.getError().getMessage());
            }

            @Override
            public void onStatusChange(StatusChangeEventArgs args) {

            }

            @Override
            public void onActivityDetection(ActivityDetectionEventArgs args) {
                Optional<Person> person = recognizer.recognize(args.getIdentificationData());
            }

            @Override
            public void onEntitiesFetched(Iterable entities) {
                System.out.println("1");
            }
        });
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
    public boolean isAlive() {
        return true;
    }
}