package whosHome.whosHomeApp.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import whosHome.common.dataProviders.ISensorConnectionsMetadataDao;
import whosHome.common.dataProviders.ISensorTypesMetadataDao;
import whosHome.common.dataProviders.db.Hibernate;
import whosHome.common.dataProviders.db.SensorConnectionsMetadataDbDao;
import whosHome.common.dataProviders.db.SensorTypesMetadataDbDao;
import whosHome.common.sensors.client.ISensorConnection;
import whosHome.whosHomeApp.Application;
import whosHome.whosHomeApp.dataAccess.IDevicesDao;
import whosHome.whosHomeApp.dataAccess.IPeopleDao;
import whosHome.whosHomeApp.dataAccess.agents.PeopleServiceAgent;
import whosHome.whosHomeApp.dataAccess.db.DevicesDbDao;
import whosHome.whosHomeApp.engine.WhosHomeEngine;
import whosHome.whosHomeApp.engine.recognition.PeopleRecognitionManager;
import whosHome.whosHomeApp.engine.sensors.ISensorConnectionsFactory;
import whosHome.whosHomeApp.engine.sensors.SensorConnectionsFactory;
import whosHome.whosHomeApp.utils.mocks.DevicesDaoMock;
import whosHome.whosHomeApp.utils.mocks.PeopleDaoMock;

@Configuration
@PropertySource("classpath:application.${env}.properties")
public class ApplicationContextConfiguration {
    @Autowired Environment env;

    @Bean
    public Hibernate hibernate() {
        return new Hibernate.Builder()
                .withServerUrl(env.getProperty("mysql.url"), env.getProperty("mysql.dbname"))
                .withUsername(env.getProperty("mysql.username"))
                .withPassword(env.getProperty("mysql.password"))
                .build();
    }

    @Bean
    public IPeopleDao peopleDao(@Value("${people-service.url}") String serviceUrl,
                                @Value("${people-service.api-path}") String peopleApiPath) {
        if (env() == Application.Environment.DEBUG) {
            return new PeopleDaoMock();
        }
        return new PeopleServiceAgent(serviceUrl, peopleApiPath, new RestTemplate());
    }

    @Bean
    public ISensorConnectionsMetadataDao sensorConnectionsMetadataDao() {
        return new SensorConnectionsMetadataDbDao(hibernate());
    }

    @Bean
    public ISensorTypesMetadataDao sensorTypesMetadataDao() {
        return new SensorTypesMetadataDbDao(hibernate());
    }

    @Bean
    public IDevicesDao devicesDao() {
        if (env() == Application.Environment.DEBUG) {
            return new DevicesDaoMock();
        }
        return new DevicesDbDao(hibernate());
    }

    @Bean
    public ISensorConnectionsFactory sensorConnectionsFactory() {
        return new SensorConnectionsFactory(sensorConnectionsMetadataDao());
    }

    @Bean @Autowired
    public PeopleRecognitionManager peopleRecognitionManager(IPeopleDao peopleDao, IDevicesDao devicesDao) {
        return new PeopleRecognitionManager(peopleDao, devicesDao);
    }

    @Bean @Autowired
    public WhosHomeEngine whosHomeEngine(PeopleRecognitionManager recognitionManager,
                                         ISensorConnectionsFactory sensorConnectionsFactory) {
        return new WhosHomeEngine(recognitionManager, sensorConnectionsFactory);
    }

    private Application.Environment env() {
        String env = this.env.getProperty("env");
        return Application.Environment.valueOf(env.toUpperCase());
    }
}
