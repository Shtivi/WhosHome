package whosHome.whosHomeApp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.cli.Options;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.cli.*;
import whosHome.common.dataProviders.ISensorConnectionsMetadataDao;
import whosHome.whosHomeApp.engine.WhosHomeEngine;

@RestController
@SpringBootApplication
@EnableAutoConfiguration
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
        System.setProperty("env", env);

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        WhosHomeEngine engine = context.getBean(WhosHomeEngine.class);
//
//        Config config = ConfigFactory.load(String.format("application.%s", env.toLowerCase()));
//
//        AppModule appModule = new AppModule(config.getConfig("whosHome"), Environment.valueOf(env.toUpperCase()));
//        Injector injector = Guice.createInjector(appModule);
//        WhosHomeEngine whosHomeEngine = injector.getInstance(WhosHomeEngine.class);
//        whosHomeEngine.start();
//        whosHomeEngine.onActivityDetection().listen((detectionArgs) -> {
//            System.out.println(detectionArgs.getSubject().getFirstname());
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
    public boolean isAlive() {
        return true;
    }
}