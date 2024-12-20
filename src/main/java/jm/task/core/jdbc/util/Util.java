package jm.task.core.jdbc.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceProvider;

import org.hibernate.HibernateException;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class Util {
    private static final String URL = "db.url";
    private static final String USERNAME = "db.username";
    private static final String PASSWORD = "db.password";
    private static final String DRIVERH = "jakarta.persistence.jdbc.driver";
    private static final String URLH = "jakarta.persistence.jdbc.url";
    private static final String USERH = "jakarta.persistence.jdbc.user";
    private static final String PASSWORDH = "jakarta.persistence.jdbc.password";
    private static final String SHOWSQL = "show_sql";
    private static final String FORMATSQL = "format_sql";

    private Util() {
    }

    public static Connection getConnection()
            throws SQLException, RuntimeException {

        return DriverManager.getConnection(
                Property.get(URL),
                Property.get(USERNAME),
                Property.get(PASSWORD));
    }

    public static EntityManagerFactory getEntityManagerFactory()
            throws HibernateException {

        Map<String, String> properties = new HashMap<>();

        properties.put(DRIVERH, Property.get(DRIVERH));
        properties.put(URLH, Property.get(URLH));
        properties.put(USERH, Property.get(USERH));
        properties.put(PASSWORDH, Property.get(PASSWORDH));
        properties.put(SHOWSQL, Property.get(SHOWSQL));
        properties.put(FORMATSQL, Property.get(FORMATSQL));

        PersistenceProvider provider = new HibernatePersistenceProvider();
        return provider
                .createContainerEntityManagerFactory(
                        PersistenceUnitInfoImpl.getInstance(),
                        properties);
    }
}