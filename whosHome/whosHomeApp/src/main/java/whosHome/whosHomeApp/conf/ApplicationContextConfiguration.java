package whosHome.whosHomeApp.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import whosHome.common.dataProviders.ISensorConnectionsMetadataDao;
import whosHome.common.dataProviders.db.Hibernate;
import whosHome.common.dataProviders.db.SensorConnectionsMetadataDbDao;
import whosHome.whosHomeApp.engine.WhosHomeEngine;

@Configuration
@PropertySource("classpath:application.${env}.properties")
public class ApplicationContextConfiguration {
    @Autowired Environment env;

    @Bean
    public Hibernate hibernate() {
        // TODO: 8/8/2018 add read from config file
        return new Hibernate.Builder()
                .withServerUrl(env.getProperty("mysql.url"), env.getProperty("mysql.dbname"))
                .withUsername(env.getProperty("mysql.username"))
                .withPassword(env.getProperty("mysql.password"))
                .build();
    }

    @Bean
    public ISensorConnectionsMetadataDao sensorConnectionsMetadataDao() {
        return new SensorConnectionsMetadataDbDao(hibernate());
    }
//
//    @Bean
//    public WhosHomeEngine whosHomeEngine() {
//        return new WhosHomeEngine(null, null);
//    }
}
