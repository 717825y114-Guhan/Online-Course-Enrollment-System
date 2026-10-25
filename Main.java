package main;

import exception.EnrollmentException;
import model.Course;
import model.Enrollment;
import model.PaymentMethod;
import model.PremiumStudent;
import model.Receipt;
import model.Student;
import service.CourseEnrollmentService;
import util.InputUtil;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Entry point: menu-driven Online Course Enrollment System.
 */
public class Main {

    private static final CourseEnrollmentService service = new CourseEnrollmentService();

    public static void main(String[] args) {
        System.out.println("*****************************************************");
        System.out.println("*        ONLINE COURSE ENROLLMENT SYSTEM            *");
        System.out.println("*****************************************************");

        boolean running = true;
        while (running) {
            try {
                printMenu();
                int choice = InputUtil.readInt("Enter your choice (1-6): ", 1, 6);
                switch (choice) {
                    case 1: addCourse();         break;
                    case 2: addStudent();        break;
                    case 3: enrollInCourse();    break;
                    case 4: makePayment();       break;
                    case 5: displayCourses();    break;
                    case 6:
                        System.out.println("\nThank you for using the system. Goodbye!");
                        running = false;
                        break;
                    default:
                        break;
                }
            } catch (EnrollmentException e) {
                System.out.println("\n  [ERROR] " + e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println("\nInput closed. Exiting the program.");
                running = false;
            } catch (RuntimeException e) {
                System.out.println("\n  [ERROR] Unexpected problem: " + e.getMessage());
            }
        }
    }

    // ------------------------------------------------------------------
    private static void printMenu() {
        System.out.println();
        System.out.println("================= MAIN MENU =================");
        System.out.println("  1. Add Course");
        System.out.println("  2. Add Student");
        System.out.println("  3. Enroll in Course");
        System.out.println("  4. Make Payment");
        System.out.println("  5. Display Courses");
        System.out.println("  6. Exit");
        System.out.println("=============================================");
    }

    // ------------------------------------------------------------------
    // 1. Add Course
    // ------------------------------------------------------------------
    private static void addCourse() throws EnrollmentException {
        System.out.println("\n--- Add Course ---");
        String title = InputUtil.readTitle("Course title            : ");
        double fee = InputUtil.readDouble("Course fee (Rs.)        : ", 1, 1000000);
        int capacity = InputUtil.readInt("Seat capacity           : ", 1, 500);
        String instructor = InputUtil.readName("Instructor name         : ");
        String specialization = InputUtil.readTitle("Instructor speciality   : ");

        Course c = service.addCourse(title, fee, capacity, instructor, specialization);
        System.out.println("\n  Course added successfully!");
        System.out.printf("  ID: %s | %s | Fee: Rs. %.2f | Seats: %d | Instructor: %s%n",
                c.getCourseId(), c.getTitle(), c.getFee(), c.getCapacity(), c.getInstructor().getName());
    }

    // ------------------------------------------------------------------
    // 2. Add Student
    // ------------------------------------------------------------------
    private static void addStudent() throws EnrollmentException {
        System.out.println("\n--- Add Student ---");
        String name = InputUtil.readName("Student name           : ");
        String email = InputUtil.readEmail("E-mail                 : ");
        int type = InputUtil.readInt("Type (1=Regular, 2=Premium): ", 1, 2);

        Student s = service.addStudent(name, email, type == 2);
        System.out.println("\n  Student added successfully!");
        System.out.printf("  ID: %s | %s | Type: %s%n", s.getStudentId(), s.getName(), s.getStudentType());
        if (s instanceof PremiumStudent) {
            System.out.printf("  Benefits: %.0f%% fee discount + extra course materials%n",
                    s.getDiscountRate() * 100);
        }
    }

    // ------------------------------------------------------------------
    // 3. Enroll in Course
    // ------------------------------------------------------------------
    private static void enrollInCourse() throws EnrollmentException {
        System.out.println("\n--- Enroll in Course ---");
        if (service.getCourses().isEmpty() || service.getStudents().isEmpty()) {
            System.out.println("  Please add at least one course and one student first.");
            return;
        }
        String studentId = InputUtil.readId("Student ID (e.g. STU1001): ");
        String courseId = InputUtil.readId("Course ID  (e.g. CRS101) : ");

        Enrollment e = service.enroll(studentId, courseId);
        Student s = e.getStudent();
        Course c = e.getCourse();

        System.out.println();
        System.out.println("======= ENROLLMENT REGISTERED - PAYMENT PENDING =======");
        System.out.printf("Enrollment ID   : %s%n", e.getEnrollmentId());
        System.out.printf("Student         : %s%n", s);
        System.out.printf("Course          : %s%n", c);
        System.out.printf("Instructor      : %s%n", c.getInstructor().getName());
        System.out.printf("Course Fee      : Rs. %.2f%n", e.getCourseFee());
        if (s.getDiscountRate() > 0) {
            System.out.printf("Premium Discount: Rs. %.2f (%.0f%% off)%n",
                    e.getDiscount(), s.getDiscountRate() * 100);
            System.out.println("Extra Materials : Access will be granted");
        } else {
            System.out.println("Discount        : Not applicable (Regular student)");
        }
        System.out.printf("Amount Payable  : Rs. %.2f%n", e.getAmountPayable());
        System.out.printf("Seats Remaining : %d of %d%n", c.getAvailableSeats(), c.getCapacity());
        System.out.printf("Status          : %s%n", e.getStatus());
        System.out.println("Use 'Make Payment' to confirm this enrollment.");
        System.out.println("=======================================================");
    }

    // ------------------------------------------------------------------
    // 4. Make Payment
    // ------------------------------------------------------------------
    private static void makePayment() throws EnrollmentException {
        System.out.println("\n--- Make Payment ---");
        List<Enrollment> pending = service.getPendingEnrollments();
        if (pending.isEmpty()) {
            System.out.println("  No enrollments are waiting for payment.");
            return;
        }
        System.out.println("Pending enrollments:");
        for (Enrollment p : pending) {
            System.out.printf("  %s | %-12s | %-20s | Payable: Rs. %.2f%n", p.getEnrollmentId(),
                    p.getStudent().getName(), p.getCourse().getTitle(), p.getAmountPayable());
        }

        String enrollmentId = InputUtil.readId("\nEnrollment ID (e.g. ENR5001): ");
        Enrollment e = service.findEnrollment(enrollmentId);
        System.out.printf("Amount payable: Rs. %.2f%n", e.getAmountPayable());

        System.out.println("Payment method: 1=Cash  2=Card  3=UPI  4=Net Banking");
        int m = InputUtil.readInt("Select method (1-4)   : ", 1, 4);
        PaymentMethod method = PaymentMethod.values()[m - 1];
        double amount = InputUtil.readDouble("Amount to pay (Rs.)   : ", 0.01, 1000000);

        Receipt receipt = service.makePayment(enrollmentId, method, amount);

        System.out.println("\n  Payment successful! Enrollment CONFIRMED.\n");
        receipt.print();
        System.out.printf("Course '%s' seats remaining: %d of %d%n", receipt.getCourse().getTitle(),
                receipt.getCourse().getAvailableSeats(), receipt.getCourse().getCapacity());
    }

    // ------------------------------------------------------------------
    // 5. Display Courses
    // ------------------------------------------------------------------
    private static void displayCourses() {
        System.out.println("\n--- Available Courses ---");
        List<Course> list = service.getCourses();
        if (list.isEmpty()) {
            System.out.println("  No courses available yet.");
            return;
        }
        String row = "%-7s %-22s %-16s %10s %9s %6s %-7s%n";
        System.out.printf(row, "ID", "Title", "Instructor", "Fee (Rs.)", "Premium", "Seats", "Status");
        System.out.println("-".repeat(84));
        for (Course c : list) {
            double premiumFee = c.getFee() * (1 - PremiumStudent.PREMIUM_DISCOUNT_RATE);
            String seats = c.getAvailableSeats() + "/" + c.getCapacity();
            System.out.printf(row, c.getCourseId(), c.getTitle(), c.getInstructor().getName(),
                    String.format("%.2f", c.getFee()), String.format("%.2f", premiumFee),
                    seats, c.isAvailable() ? "OPEN" : "FULL");
        }
        System.out.println("-".repeat(84));
        System.out.println("Seats = available/total.  Premium = fee after 20% premium discount.");
    }
}
