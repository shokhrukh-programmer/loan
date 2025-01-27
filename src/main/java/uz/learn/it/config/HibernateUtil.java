package uz.learn.it.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import uz.learn.it.entity.*;

import java.util.Properties;

@Slf4j
public class HibernateUtil {
    private static SessionFactory buildSessionJavaConfigFactory() {
        try {
            Configuration configuration = new Configuration();

            Properties props = new Properties();
            props.put("hibernate.connection.driver_class", "org.postgresql.Driver");
            props.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/loan_management");
            props.put("hibernate.connection.username", "postgres");
            props.put("hibernate.connection.password", "1");
            props.put("hibernate.current_session_context_class", "thread");
            props.put("hibernate.hbm2ddl.auto", "create");
            props.put("hibernate.show_sql", "true");
            props.put("hibernate.hbm2ddl.drop_cascade", "true");

            configuration.setProperties(props);

            configuration.addAnnotatedClass(Account.class);
            configuration.addAnnotatedClass(Client.class);
            configuration.addAnnotatedClass(DailyLoanPaymentDebt.class);
            configuration.addAnnotatedClass(Loan.class);
            configuration.addAnnotatedClass(TransactionHistory.class);
            configuration.addAnnotatedClass(UserCredential.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

            log.info("Hibernate Java Config serviceRegistry created");

            return configuration.buildSessionFactory(serviceRegistry);
        }
        catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed.");

            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionJavaConfigFactory() {
        return buildSessionJavaConfigFactory();
    }
}
