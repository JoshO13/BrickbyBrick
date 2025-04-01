import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // Replace with your Oracle DB details
    private static final String URL = "jdbc:oracle:thin:@10.110.10.90:1521:oracle";
    private static final String USER = "IT326T04";
    private static final String PASSWORD = "enemy96";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found.");
            e.printStackTrace();
            throw new SQLException(e);
        }
    }
}
