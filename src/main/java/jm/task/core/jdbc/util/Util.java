package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Connection connection;
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/kata";
    static final String ADMIN_LOGIN = "admin";
    static final String ADMIN_PASSWORD = "root";
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(JDBC_URL, ADMIN_LOGIN, ADMIN_PASSWORD);
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        Properties properties = getProperties();
        Configuration configuration = new Configuration()
                .addProperties(getProperties())
                .addAnnotatedClass(User.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(Environment.URL, JDBC_URL);
        properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.setProperty(Environment.USER, ADMIN_LOGIN);
        properties.setProperty(Environment.PASS, ADMIN_PASSWORD);
        properties.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        return properties;
    }
}
