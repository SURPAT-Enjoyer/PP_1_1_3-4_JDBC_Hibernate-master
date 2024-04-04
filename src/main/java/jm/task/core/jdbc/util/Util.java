package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/kata";
    static final String ADMIN_LOGIN = "admin";
    static final String ADMIN_PASSWORD = "root";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, ADMIN_LOGIN, ADMIN_PASSWORD);
    }

}
