package exception;

/**
 * Thrown when a payment cannot be completed (wrong amount, already paid, etc.).
 */
public class PaymentException extends EnrollmentException {
    private static final long serialVersionUID = 1L;

    public PaymentException(String message) {
        super(message);
    }
}
