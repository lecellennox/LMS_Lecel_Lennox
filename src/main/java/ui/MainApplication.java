package ui;
// Lecel Lennox, Software Dev I, CEN 3024C, 11/17/24

import database.DatabaseHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

//MainApplication class launches the UI and integrates with database


public class MainApplication extends Application {
    private DatabaseHelper dbHelper;

    //Main method to launch JavaFx
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {

        dbHelper = new DatabaseHelper("C:/Users/camer/LibraryManagementSystem/library.db");

        // Root layout for UI
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.DARKOLIVEGREEN, null, null)));

        // TableView for the database
        TableView<String[]> tableView = new TableView<>();
        initializeTableColumns(tableView);

        // Buttons for actions
        Button refreshBtn = new Button("Refresh Books");
        Button checkOutBtn = new Button("Check Out");
        Button checkInBtn = new Button("Check In");
        Button removeBtn = new Button("Remove Book");

        // Layout for buttons
        HBox buttonBox = new HBox(10, refreshBtn, checkOutBtn, checkInBtn, removeBtn);
        buttonBox.setStyle("-fx-padding: 10; -fx-alignment: center;");

        // Set layout positions
        root.setTop(buttonBox);
        root.setCenter(tableView);

        // Event handler for user actions
        refreshBtn.setOnAction(event -> refreshBooks(tableView));
        checkOutBtn.setOnAction(event -> handleCheckOut());
        checkInBtn.setOnAction(event -> handleCheckIn());
        removeBtn.setOnAction(event -> handleRemove());

        // Set up the scene and stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Library Management System");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load initial database
        refreshBooks(tableView);
    }
    //Setting the Tableview Columns
    private void initializeTableColumns(TableView<String[]> tableView) {
        // Define and add columns to the table
        String[] columnNames = {"Barcode", "Title", "Author", "Genre", "Status", "Due Date"};
        for (int i = 0; i < columnNames.length; i++) {
            TableColumn<String[], String> column = new TableColumn<>(columnNames[i]);
            final int colIndex = i;
            column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[colIndex]));
            column.setPrefWidth(120); //
            tableView.getColumns().add(column);
        }
    }
    //Method that refreshes the UI to provide the latest changes in the database
    private void refreshBooks(TableView<String[]> tableView) {
        tableView.getItems().clear(); // Clear existing data
        try {
            ResultSet rs = dbHelper.fetchBooks();
            while (rs.next()) {
                tableView.getItems().add(new String[]{
                        rs.getString("barcode"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getString("status"),
                        rs.getString("due_date")
                });
            }
        } catch (SQLException e) {
            showAlert("Error loading books: " + e.getMessage());
        }
    }

    private void handleCheckOut() {
        handleBookUpdate("Check Out Book", "Enter the Barcode to Check Out:", "checked out");
    }

    private void handleCheckIn() {
        handleBookUpdate("Check In Book", "Enter the Barcode to Check In:", "checked in");
    }

    private void handleRemove() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Remove Book");
        dialog.setHeaderText("Enter the Barcode to Remove:");
        dialog.showAndWait().ifPresent(barcode -> {
            try {
                dbHelper.removeBook(barcode);
                showAlert("Book removed successfully!");
            } catch (SQLException e) {
                showAlert("Failed to remove book: " + e.getMessage());
            }
        }
        );
    }

    private void handleBookUpdate(String title, String headerText, String newStatus) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.showAndWait().ifPresent(barcode -> {
            try {
                String dueDate = newStatus.equals("checked out") ? DatabaseHelper.calculateDueDate() : null;
                dbHelper.updateBookStatus(barcode, newStatus, dueDate);
                String message = newStatus.equals("checked out")
                        ? "Book checked out successfully! Due date: " + dueDate
                        : "Book checked in successfully!";
                showAlert(message);
            } catch (SQLException e) {
                showAlert("Failed to update book status: " + e.getMessage());
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    // Close database connection on application exit
    @Override
    public void stop() {
        dbHelper.closeConnection();
    }
}