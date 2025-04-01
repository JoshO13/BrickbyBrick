import java.sql.*;

public class Database {
    // Database URL syntax: jdbc:oracle:thin:@//host:port/service
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/orclpdb";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        try {
            // Load the Oracle JDBC driver (optional with newer JDBC drivers)
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish connection
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Oracle DB!");

            // Execute a simple query
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM YOUR_TABLE");

            // Process the results
            while(rs.next()) {
                System.out.println("Column Data: " + rs.getString("YOUR_COLUMN"));
            }

            // Close resources
            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
