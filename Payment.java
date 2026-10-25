package model;

import java.time.LocalDateTime;

/**
 * A completed payment made against one Enrollment.
 */
public class Payment {
    private final String paymentId;
    private final Enrollment enrollment;   // aggregation
    private final double amount;
    private final PaymentMethod method;
    private final LocalDateTime paidOn;

    public Payment(String paymentId, Enrollment enrollment, double amount, PaymentMethod method) {
        this.paymentId = paymentId;
        this.enrollment = enrollment;
        this.amount = amount;
        this.method = method;
        this.paidOn = LocalDateTime.now();
    }

    public String getPaymentId()       { return paymentId; }
    public Enrollment getEnrollment()  { return enrollment; }
    public double getAmount()          { return amount; }
    public PaymentMethod getMethod()   { return method; }
    public LocalDateTime getPaidOn()   { return paidOn; }
}
