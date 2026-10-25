package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Receipt issued after payment. Linked directly to a Student and a Course.
 */
public class Receipt {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");

    private final String receiptNo;
    private final Student student;      // link to student
    private final Course course;        // link to course
    private final Payment payment;
    private final LocalDateTime issuedOn;

    public Receipt(String receiptNo, Payment payment) {
        this.receiptNo = receiptNo;
        this.payment = payment;
        this.student = payment.getEnrollment().getStudent();
        this.course = payment.getEnrollment().getCourse();
        this.issuedOn = LocalDateTime.now();
    }

    public String getReceiptNo()  { return receiptNo; }
    public Student getStudent()   { return student; }
    public Course getCourse()     { return course; }
    public Payment getPayment()   { return payment; }

    /** Prints the receipt to the console. */
    public void print() {
        Enrollment e = payment.getEnrollment();
        String line = "=".repeat(54);
        System.out.println(line);
        System.out.println("            COURSE ENROLLMENT RECEIPT");
        System.out.println(line);
        System.out.printf("Receipt No      : %s%n", receiptNo);
        System.out.printf("Date            : %s%n", issuedOn.format(FORMAT));
        System.out.printf("Enrollment ID   : %s%n", e.getEnrollmentId());
        System.out.printf("Student         : %s (%s)%n", student.getName(), student.getStudentId());
        System.out.printf("Student Type    : %s%n", student.getStudentType());
        System.out.printf("Course          : %s (%s)%n", course.getTitle(), course.getCourseId());
        System.out.printf("Instructor      : %s%n", course.getInstructor().getName());
        System.out.println("-".repeat(54));
        System.out.printf("Course Fee      : Rs. %10.2f%n", e.getCourseFee());
        if (student.getDiscountRate() > 0) {
            System.out.printf("Premium Discount: Rs. %10.2f  (%.0f%% off)%n",
                    e.getDiscount(), student.getDiscountRate() * 100);
        } else {
            System.out.println("Discount        : Not applicable (Regular student)");
        }
        System.out.printf("Amount Paid     : Rs. %10.2f%n", payment.getAmount());
        System.out.println("-".repeat(54));
        System.out.printf("Payment ID      : %s%n", payment.getPaymentId());
        System.out.printf("Payment Method  : %s%n", payment.getMethod());
        System.out.printf("Extra Materials : %s%n", student.hasExtraMaterials() ? "Access granted" : "No");
        System.out.printf("Status          : %s%n", e.getStatus());
        System.out.println(line);
    }
}
