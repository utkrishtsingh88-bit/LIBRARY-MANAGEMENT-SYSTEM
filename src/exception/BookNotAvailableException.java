package exception;

/**
 * Custom checked exception thrown when a requested book is not available for issue.
 */
public class BookNotAvailableException extends Exception {
    private static final long serialVersionUID = 1L;

    public BookNotAvailableException(String message) {
        super(message);
    }
}
