package service;

import exception.CourseFullException;
import exception.DuplicateRecordException;
import exception.PaymentException;
import exception.RecordNotFoundException;
import model.Course;
import model.Enrollment;
import model.Instructor;
import model.Payment;
import model.PaymentMethod;
import model.PremiumStudent;
import model.Receipt;
import model.Student;
import util.IdGenerator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Business logic of the Course Enrollment System.
 * Holds all courses, students, enrollments, payments and receipts.
 */
public class CourseEnrollmentService {

    private final Map<String, Course> courses = new LinkedHashMap<>();
    private final Map<String, Student> students = new LinkedHashMap<>();
    private final Map<String, Enrollment> enrollments = new LinkedHashMap<>();
    private final List<Payment> payments = new ArrayList<>();
    private final List<Receipt> receipts = new ArrayList<>();

    // ------------------------------------------------------------------
    // Add Course / Add Student
    // ------------------------------------------------------------------
    public Course addCourse(String title, double fee, int capacity,
                            String instructorName, String specialization) throws DuplicateRecordException {
        for (Course c : courses.values()) {
            if (c.getTitle().equalsIgnoreCase(title)) {
                throw new DuplicateRecordException("A course titled '" + title + "' already exists.");
            }
        }
        Instructor instructor = new Instructor(IdGenerator.nextInstructorId(), instructorName, specialization);
        Course course = new Course(IdGenerator.nextCourseId(), title, fee, capacity, instructor);
        courses.put(course.getCourseId(), course);
        return course;
    }

    public Student addStudent(String name, String email, boolean premium) throws DuplicateRecordException {
        for (Student s : students.values()) {
            if (s.getEmail().equalsIgnoreCase(email)) {
                throw new DuplicateRecordException("A student with e-mail '" + email + "' already exists.");
            }
        }
        String id = IdGenerator.nextStudentId();
        Student student = premium ? new PremiumStudent(id, name, email)   // polymorphic reference
                                  : new Student(id, name, email);
        students.put(id, student);
        return student;
    }

    // ------------------------------------------------------------------
    // Enroll in Course  (rules 1 and 4: only available courses; capacity updates)
    // ------------------------------------------------------------------
    public Enrollment enroll(String studentId, String courseId)
            throws RecordNotFoundException, DuplicateRecordException, CourseFullException {
        Student student = findStudent(studentId);
        Course course = findCourse(courseId);

        for (Enrollment e : enrollments.values()) {
            if (e.getStudent() == student && e.getCourse() == course) {
                throw new DuplicateRecordException(student.getName()
                        + " is already enrolled in '" + course.getTitle() + "' (" + e.getEnrollmentId() + ").");
            }
        }

        course.reserveSeat();      // throws CourseFullException when no seat is left
        Enrollment enrollment = new Enrollment(IdGenerator.nextEnrollmentId(), student, course);
        enrollments.put(enrollment.getEnrollmentId(), enrollment);
        return enrollment;
    }

    // ------------------------------------------------------------------
    // Make Payment  (rule 3: enrollment is confirmed only after payment)
    // ------------------------------------------------------------------
    public Receipt makePayment(String enrollmentId, PaymentMethod method, double amount)
            throws RecordNotFoundException, PaymentException {
        Enrollment enrollment = findEnrollment(enrollmentId);

        if (enrollment.isConfirmed()) {
            throw new PaymentException("Enrollment " + enrollmentId + " is already paid and confirmed.");
        }
        if (Math.abs(amount - enrollment.getAmountPayable()) > 0.001) {
            throw new PaymentException(String.format(
                    "Payment failed: amount Rs. %.2f does not match the amount payable Rs. %.2f.",
                    amount, enrollment.getAmountPayable()));
        }

        Payment payment = new Payment(IdGenerator.nextPaymentId(), enrollment, amount, method);
        payments.add(payment);
        enrollment.confirm();

        Receipt receipt = new Receipt(IdGenerator.nextReceiptNo(), payment);
        receipts.add(receipt);
        return receipt;
    }

    // ------------------------------------------------------------------
    // Lookups
    // ------------------------------------------------------------------
    public Course findCourse(String id) throws RecordNotFoundException {
        Course c = courses.get(id);
        if (c == null) {
            throw new RecordNotFoundException("Course", id);
        }
        return c;
    }

    public Student findStudent(String id) throws RecordNotFoundException {
        Student s = students.get(id);
        if (s == null) {
            throw new RecordNotFoundException("Student", id);
        }
        return s;
    }

    public Enrollment findEnrollment(String id) throws RecordNotFoundException {
        Enrollment e = enrollments.get(id);
        if (e == null) {
            throw new RecordNotFoundException("Enrollment", id);
        }
        return e;
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses.values());
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students.values());
    }

    /** Enrollments that are waiting for payment. */
    public List<Enrollment> getPendingEnrollments() {
        List<Enrollment> pending = new ArrayList<>();
        for (Enrollment e : enrollments.values()) {
            if (!e.isConfirmed()) {
                pending.add(e);
            }
        }
        return pending;
    }
}
