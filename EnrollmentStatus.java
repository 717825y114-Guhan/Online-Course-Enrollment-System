package model;

/**
 * Life-cycle of an enrollment: it is confirmed only after payment.
 */
public enum EnrollmentStatus {
    PENDING_PAYMENT("PENDING PAYMENT"),
    CONFIRMED("CONFIRMED");

    private final String label;

    EnrollmentStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
