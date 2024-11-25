package database;
// Lecel Lennox, Software Dev I, CEN 3024C, 11/17/24
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** The {@code DatabaseHelper} class provides methods for interacting with the SQLite database
 * used in the Library Management System.Retrieving book records, changing book statuses, deleting books,
 * and calculating due dates for checked-out books are all supported by this class.
 */
public class DatabaseHelper {
    private Connection conn;

    /**
     * Constructs a {@code DatabaseHelper} object and establishes a connection to the SQLite database.
     *
     * @param dbPath the path to the SQLite database file
     */
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

    /**
     * Fetches all book records from the database.
     *
     * @return a {@code ResultSet} containing the book records
     * @throws SQLException if a database access error occurs
     */
    public ResultSet fetchBooks() throws SQLException {
        String query = "SELECT * FROM books"; // Query to fetch all book records
        return conn.createStatement().executeQuery(query);
    }

    /**
     * Updates the status and due date of a book identified by its barcode.
     *
     * @param barcode the unique barcode of the book to update
     * @param status  the new status of the book (e.g., "checked out", "checked in")
     * @param dueDate the new due date for the book, or {@code null} if not applicable
     * @throws SQLException if a database access error occurs
     */
    public void updateBookStatus(String barcode, String status, String dueDate) throws SQLException {
        String query = "UPDATE books SET status = ?, due_date = ? WHERE barcode = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setString(2, dueDate);
            pstmt.setString(3, barcode);
            pstmt.executeUpdate();
        }
    }

    /**
     * Removes a book from the database based on its barcode.
     *
     * @param barcode the unique barcode of the book to remove
     * @throws SQLException if a database access error occurs
     */
    public void removeBook(String barcode) throws SQLException {
        String query = "DELETE FROM books WHERE barcode = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, barcode);
            pstmt.executeUpdate();
        }
    }

    /**
     * Closes the database connection.
     * This method should be called when the application is shutting down
     * to ensure proper resource management.
     */
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

    /**
     * Calculates a due date for books that are checked out.
     * The due date is set to four weeks from the current date.
     *
     * @return the calculated due date as a {@code String} in the format "yyyy-MM-dd"
     */
    public static String calculateDueDate() {
        return LocalDate.now().plusWeeks(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
