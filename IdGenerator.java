package util;

/**
 * Generates sequential, human-readable IDs such as CRS101 or STU1001.
 */
public final class IdGenerator {
    private static int courseSeq = 100;
    private static int instructorSeq = 200;
    private static int studentSeq = 1000;
    private static int enrollmentSeq = 5000;
    private static int paymentSeq = 9000;
    private static int receiptSeq = 7000;

    private IdGenerator() {
        // utility class - no objects
    }

    public static String nextCourseId()     { return "CRS" + (++courseSeq); }
    public static String nextInstructorId() { return "INS" + (++instructorSeq); }
    public static String nextStudentId()    { return "STU" + (++studentSeq); }
    public static String nextEnrollmentId() { return "ENR" + (++enrollmentSeq); }
    public static String nextPaymentId()    { return "PAY" + (++paymentSeq); }
    public static String nextReceiptNo()    { return "RCP" + (++receiptSeq); }
}
