package exception;

/**
 * Thrown when a student tries to enroll in a course that has no seats left.
 */
public class CourseFullException extends EnrollmentException {
    private static final long serialVersionUID = 1L;

    public CourseFullException(String courseTitle) {
        super("Course '" + courseTitle + "' is full. No seats available.");
    }
}
