package whosHome.whosHomeApp;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        Config config = ConfigFactory.load("application");
    }

    @RequestMapping(value = "/isAlive", method = RequestMethod.GET)
    public boolean greeting() {
        return true;
    }
}