package whosHome.common.dataProviders.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import whosHome.common.models.SensorConnectionMetadata;
import whosHome.common.models.SensorTypeMetadata;

public class Hibernate {
    private SessionFactory sessionFactory;
    private ServiceRegistry serviceRegistry;

    public Hibernate(String hibernateResourceFilename) {
        Configuration conf = new Configuration().configure(hibernateResourceFilename).configure();
        serviceRegistry = new ServiceRegistryBuilder().applySettings(conf.getProperties()).buildServiceRegistry();
        try {
            sessionFactory = conf.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
