package exception;

/**
 * Custom checked exception thrown when a member is not found in the system.
 */
public class MemberNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public MemberNotFoundException(String message) {
        super(message);
    }
}
