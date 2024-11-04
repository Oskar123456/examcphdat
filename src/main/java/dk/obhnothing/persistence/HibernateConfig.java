package dk.obhnothing.persistence;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.obhnothing.persistence.ent.AppointmentEnt;
import dk.obhnothing.persistence.ent.DoctorEnt;
import dk.obhnothing.persistence.ent.Headline;
import dk.obhnothing.persistence.ent.Plant;
import dk.obhnothing.persistence.ent.Reseller;
import dk.obhnothing.persistence.ent.Source;
import dk.obhnothing.security.entities.Role;
import dk.obhnothing.security.entities.User;
import dk.obhnothing.utilities.Utils;
import jakarta.persistence.EntityManagerFactory;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-04.............
 * -----------------------
 */

public class HibernateConfig {
    private static Logger logger = LoggerFactory.getLogger(HibernateConfig.class);
    private static EntityManagerFactory emf;
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null)
            throw new RuntimeException("No EntityManagerFactory Instance");
        return emf;
    }

    private static void getAnnotationConfiguration(Configuration configuration) {
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Role.class);
        configuration.addAnnotatedClass(DoctorEnt.class);
        configuration.addAnnotatedClass(AppointmentEnt.class);
        configuration.addAnnotatedClass(Plant.class);
        configuration.addAnnotatedClass(Reseller.class);
        configuration.addAnnotatedClass(Headline.class);
        configuration.addAnnotatedClass(Source.class);
    }

    public static void Init(Mode mode) {
        try {
            Configuration configuration = new Configuration();
            Properties props = new Properties();
            props = setBaseProperties(props);

            if (System.getenv("DEPLOYED") != null)
                props = setDeployedProperties(props);
            else if (mode == Mode.DEV)
                props = setDevProperties(props);
            else if (mode == Mode.TEST)
                props = setTestProperties(props);

            configuration.setProperties(props);
            getAnnotationConfiguration(configuration);

            logger.info("hibernate props: " + props);

            ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties()).build();
            SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
            emf = sf.unwrap(EntityManagerFactory.class);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Properties setBaseProperties(Properties props){
        props.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.current_session_context_class", "thread");
        props.put("hibernate.show_sql", "false");
        props.put("hibernate.format_sql", "false");
        props.put("hibernate.use_sql_comments", "false");
        return props;
    }

    private static Properties setDevProperties(Properties props){
        props.put("hibernate.hikari_leakDetectionThreshold", "10000"); // leak detection
        props.setProperty("hibernate.connection.url",
                Utils.getPropertyValue("DB_CONN_STR", "config.properties")
                + Utils.getPropertyValue("DB_NAME", "config.properties"));
        props.setProperty("hibernate.connection.username",
                Utils.getPropertyValue("DB_USER", "config.properties"));
        props.setProperty("hibernate.connection.password",
                Utils.getPropertyValue("DB_PW", "config.properties"));
        return props;
    }

    private static Properties setDeployedProperties(Properties props){
        props.setProperty("hibernate.connection.url", System.getenv("DB_CONN_STR") + System.getenv("DB_NAME_GARDEN"));
        props.setProperty("hibernate.connection.username", System.getenv("DB_USER"));
        props.setProperty("hibernate.connection.password", System.getenv("DB_PW"));
        return props;
    }

    private static Properties setTestProperties(Properties props) {
        props.put("hibernate.connection.driver_class", "org.testcontainers.jdbc.ContainerDatabaseDriver");
        props.put("hibernate.connection.url", "jdbc:tc:postgresql:15.3-alpine3.18:///test_db");
        props.put("hibernate.connection.username", "postgres");
        props.put("hibernate.connection.password", "postgres");
        props.put("hibernate.archive.autodetection", "class");
        props.put("hibernate.show_sql", "false");
        props.put("hibernate.hbm2ddl.auto", "create-drop");
        return props;
    }

    public enum Mode {
        DEV, TEST, DEPLOY
    }

}
