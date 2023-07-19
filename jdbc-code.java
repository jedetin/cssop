import java.sql.*;

public class DatabaseExample {
    // Database credentials
    static final String DB_URL = "jdbc:mysql://localhost/mydatabase";
    static final String USER = "username";
    static final String PASS = "password";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Step 1: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Step 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Step 3: Execute a query to create the table (if it doesn't exist)
            stmt = conn.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS mytable ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "release_no VARCHAR(20),"
                    + "env VARCHAR(20),"
                    + "details VARCHAR(100)"
                    + ")";
            stmt.executeUpdate(createTableQuery);
            System.out.println("Table created successfully.");

            // Step 4: Insert data into the table
            String insertQuery = "INSERT INTO mytable (release_no, env, details) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, "1.0");
            insertStmt.setString(2, "Production");
            insertStmt.setString(3, "Some details about the release");
            insertStmt.executeUpdate();
            System.out.println("Data inserted successfully.");

            // Step 5: Read data from the table
            String selectQuery = "SELECT * FROM mytable";
            ResultSet resultSet = stmt.executeQuery(selectQuery);

            // Step 6: Process the result set
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String releaseNo = resultSet.getString("release_no");
                String env = resultSet.getString("env");
                String details = resultSet.getString("details");
                System.out.println("ID: " + id);
                System.out.println("Release No: " + releaseNo);
                System.out.println("Environment: " + env);
                System.out.println("Details: " + details);
                System.out.println();
            }

            // Step 7: Close the resources
            resultSet.close();
            insertStmt.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the resources in case of any exception
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
