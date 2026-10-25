package model;

/**
 * A regular student. Pays the full course fee and has no extra materials.
 * PremiumStudent overrides the discount/materials behaviour (polymorphism).
 */
public class Student {
    private final String studentId;
    private String name;
    private String email;

    public Student(String studentId, String name, String email) {
        this.studentId = studentId;
        setName(name);
        setEmail(email);
    }

    public String getStudentId() { return studentId; }
    public String getName()      { return name; }
    public String getEmail()     { return email; }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Student name cannot be empty");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid e-mail");
        }
        this.email = email;
    }

    // ---------- behaviour overridden by subclasses ----------
    /** Fraction of the fee waived (0.0 for regular students). */
    public double getDiscountRate() {
        return 0.0;
    }

    public boolean hasExtraMaterials() {
        return false;
    }

    public String getStudentType() {
        return "Regular";
    }

    /** Discount amount in rupees for a given course fee. */
    public double calculateDiscount(double courseFee) {
        return courseFee * getDiscountRate();
    }

    /** Amount the student actually has to pay. */
    public double calculatePayable(double courseFee) {
        return courseFee - calculateDiscount(courseFee);
    }

    @Override
    public String toString() {
        return name + " (" + studentId + ") [" + getStudentType() + "]";
    }
}
