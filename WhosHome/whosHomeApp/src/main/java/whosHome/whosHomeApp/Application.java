package whosHome.whosHomeApp;

import org.apache.commons.cli.Options;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.cli.*;
import whosHome.whosHomeApp.engine.WhosHomeEngine;
import whosHome.whosHomeApp.infra.WhosHomeEngineDestructor;
import whosHome.whosHomeApp.infra.WhosHomeEngineInitializer;

@RestController
@SpringBootApplication
@EnableAutoConfiguration
public class Application {
    public enum Environment { DEBUG, PROD }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("Application");
        logger.info("Starting initialization");

        CommandLine cli = parseArgs(args);
        String env = cli.getOptionValue("environment");
        System.setProperty("env", env);

        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
                .listeners(new WhosHomeEngineInitializer(), new WhosHomeEngineDestructor())
                .build()
                .run(args);

        WhosHomeEngine engine = context.getBean(WhosHomeEngine.class);
//        engine.onEngineStatusChanged().listen((eventArg -> {
//            System.out.println(eventArg.getNewStatus());
//        }));
    }

    private static CommandLine parseArgs(String[] args) {
        Option environmentOption = Option.builder()
                .longOpt("environment")
                .desc("Set the environment configuration [prod / debug]")
                .hasArg()
                .required()
                .build();

        Options cliOptions = new Options().addOption(environmentOption);
        DefaultParser cliParser = new DefaultParser();
        CommandLine cli = null;
        try {
            cli = cliParser.parse(cliOptions, args);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return cli;
    }

    @RequestMapping(value = "/isAlive", method = RequestMethod.GET)
    public boolean isAlive() {
        return true;
    }
}