package logic;
// Lecel Lennox, Software Dev I, CEN 3024C, 11/17/24

//Book class provides the properties of a single book
public class Book {
    private String title;
    private String author;
    private String genre;
    private String barcode;
    private String status;
    private String dueDate;

    public Book(String title, String author, String genre, String barcode, String status, String dueDate) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.barcode = barcode;
        this.status = status;
        this.dueDate = dueDate;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode; }

    public String getStatus() {
        return status; }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getDueDate() {
        return dueDate;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

}
