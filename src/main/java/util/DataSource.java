package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    public static final String URL = "jdbc:mysql://localhost:3306/user";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "java";
    public static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
