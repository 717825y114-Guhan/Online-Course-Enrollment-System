package exception;

/**
 * Thrown when a course, student or enrollment ID does not exist.
 */
public class RecordNotFoundException extends EnrollmentException {
    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(String entity, String id) {
        super(entity + " with ID '" + id + "' was not found.");
    }
}
