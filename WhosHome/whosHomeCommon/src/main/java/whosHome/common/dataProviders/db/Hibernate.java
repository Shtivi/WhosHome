package whosHome.common.dataProviders.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

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

    private Hibernate(Configuration conf) {
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

    public static class Builder {
        private Configuration conf;

        public Builder() {
            conf = new Configuration();
            conf.configure();
        }

        public Builder withUsername(String username) {
            conf.setProperty("hibernate.connection.username", username);
            return this;
        }

        public Builder withPassword(String password) {
            conf.setProperty("hibernate.connection.password", password);
            return this;
        }

        public Builder withServerUrl(String url, String schemaName) {
            String fullUrl = String.format("jdbc:mysql://%s/%s", url, schemaName);
            conf.setProperty("hibernate.connection.url", fullUrl);
            return this;
        }

        public Hibernate build() {
            return new Hibernate(conf);
        }
    }
}
