package logic;
// Lecel Lennox, Software Dev I, CEN 3024C, 11/17/24

/**
 * Represents attribute of a book such as title, author, genre, barcode, status, and due date.
 */
public class Book {
    private String title;
    private String author;
    private String genre;
    private String barcode;
    private String status;
    private String dueDate;

    /**
     * Constructors to initialize the book attributes.
     *
     * @param title the title of the book
     * @param author the author of the book
     * @param genre the genre of the book
     * @param barcode the unique barcode identifier of the book
     * @param status the availability status of the book
     * @param dueDate the due date of the book if checked out
     */

    public Book(String title, String author, String genre, String barcode, String status, String dueDate) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.barcode = barcode;
        this.status = status;
        this.dueDate = dueDate;
    }

    /**
     *Gets title of the book
     * @return the title of  the book
     */
    public String getTitle() {
        return title;
    }
    /**
     * Sets the title of the book.
     *
     * @param title the new title of the book
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *Gets author of the book
     * @return the author of  the book
     */
    public String getAuthor() {
        return author;
    }
    /**
     * Sets the author of the book.
     *
     * @param author the new author of the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     *Gets genre of the book
     * @return the genre of  the book
     */
    public String getGenre() {
        return genre;
    }
    /**
     * Sets the genre of the book.
     *
     * @param genre the new genre of the book
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     *Gets barcode of the book
     * @return the barcode of  the book
     */
    public String getBarcode() {
        return barcode;
    }
    /**
     * Sets the barcode of the book.
     *
     * @param barcode the new barcode of the book
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode; }

    /**
     *Gets status of the book
     * @return the status of  the book
     */
    public String getStatus() {
        return status; }
    /**
     * Sets the current status of the book.
     *
     * @param status the new status of the book (e.g., "available", "checked out")
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the due date for returning the book.
     * @return the due date for the book once checked out, or null if not applicable
     */
    public String getDueDate() {
        return dueDate;
    }
    /**
     * Sets the due date for returning the book.
     *
     * @param dueDate the new due date for the book
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

}
