package database;
// Lecel Lennox, Software Dev I, CEN 3024C, 11/17/24
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*DatabaseHelper class provides methods for interacting with the SQLite database
  for the Library Management System.
 */
public class DatabaseHelper {
    private Connection conn;

    public DatabaseHelper(String dbPath) {
        try {
            if (!dbPath.startsWith("jdbc:sqlite:")) {
                dbPath = "jdbc:sqlite:" + dbPath;
            }
            // Database Connection
            conn = DriverManager.getConnection(dbPath);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database at " + dbPath);
            e.printStackTrace();
        }
    }

    //Display book method
    public ResultSet fetchBooks() throws SQLException {
        String query = "SELECT * FROM books"; // Query to fetch all book records
        return conn.createStatement().executeQuery(query);
    }

    //Update book status method
    public void updateBookStatus(String barcode, String status, String dueDate) throws SQLException {
        String query = "UPDATE books SET status = ?, due_date = ? WHERE barcode = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setString(2, dueDate);
            pstmt.setString(3, barcode);
            pstmt.executeUpdate();
        }
    }

    //Remove book method
    public void removeBook(String barcode) throws SQLException {
        String query = "DELETE FROM books WHERE barcode = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, barcode);
            pstmt.executeUpdate();
        }
    }

    //Close database connection
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing the database connection.");
            e.printStackTrace();
        }
    }

    //due date calculation method
    public static String calculateDueDate() {
        return LocalDate.now().plusWeeks(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
