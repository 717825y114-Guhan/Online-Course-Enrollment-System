package exception;

/**
 * Base class for all application-specific (checked) exceptions.
 */
public class EnrollmentException extends Exception {
    private static final long serialVersionUID = 1L;

    public EnrollmentException(String message) {
        super(message);
    }
}
