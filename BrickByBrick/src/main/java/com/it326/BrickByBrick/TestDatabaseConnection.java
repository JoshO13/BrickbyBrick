import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        try {
            Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();

            // Simple query to check Oracle DB connection
            ResultSet rs = stmt.executeQuery("SELECT 'Connection Successful!' AS status FROM dual");

            if (rs.next()) {
                System.out.println(rs.getString("status"));
            }

            // Close connections
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        }
    }
}
