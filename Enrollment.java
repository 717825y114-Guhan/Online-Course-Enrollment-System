package model;

import java.time.LocalDateTime;

/**
 * Links one Student to one Course (aggregation) and stores the fee,
 * discount and confirmation status.
 */
public class Enrollment {
    private final String enrollmentId;
    private final Student student;       // aggregation
    private final Course course;         // aggregation
    private final double courseFee;
    private final double discount;
    private final double amountPayable;
    private final LocalDateTime enrolledOn;
    private EnrollmentStatus status;

    public Enrollment(String enrollmentId, Student student, Course course) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.courseFee = course.getFee();
        this.discount = student.calculateDiscount(courseFee);     // polymorphic call
        this.amountPayable = student.calculatePayable(courseFee); // polymorphic call
        this.enrolledOn = LocalDateTime.now();
        this.status = EnrollmentStatus.PENDING_PAYMENT;
    }

    public String getEnrollmentId()     { return enrollmentId; }
    public Student getStudent()         { return student; }
    public Course getCourse()           { return course; }
    public double getCourseFee()        { return courseFee; }
    public double getDiscount()         { return discount; }
    public double getAmountPayable()    { return amountPayable; }
    public LocalDateTime getEnrolledOn() { return enrolledOn; }
    public EnrollmentStatus getStatus() { return status; }

    public boolean isConfirmed() {
        return status == EnrollmentStatus.CONFIRMED;
    }

    /** Called only after a successful payment. */
    public void confirm() {
        this.status = EnrollmentStatus.CONFIRMED;
    }

    @Override
    public String toString() {
        return enrollmentId + " : " + student.getName() + " -> " + course.getTitle()
                + " [" + status + "]";
    }
}
