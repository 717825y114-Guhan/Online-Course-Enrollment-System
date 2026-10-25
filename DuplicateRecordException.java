package exception;

/**
 * Thrown when a duplicate course, student or enrollment is added.
 */
public class DuplicateRecordException extends EnrollmentException {
    private static final long serialVersionUID = 1L;

    public DuplicateRecordException(String message) {
        super(message);
    }
}
