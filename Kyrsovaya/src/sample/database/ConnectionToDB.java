package sample.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDB {
    private static String url = "jdbc:mysql://localhost:3306/session1_01";
    private static String user = "root";
    private static String password = "root";

    public static Connection getNewConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);

    }


}
