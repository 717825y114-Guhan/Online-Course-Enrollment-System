package model;

/**
 * A premium student: gets a fee discount and access to extra materials.
 */
public class PremiumStudent extends Student {
    public static final double PREMIUM_DISCOUNT_RATE = 0.20;   // 20 %

    public PremiumStudent(String studentId, String name, String email) {
        super(studentId, name, email);
    }

    @Override
    public double getDiscountRate() {
        return PREMIUM_DISCOUNT_RATE;
    }

    @Override
    public boolean hasExtraMaterials() {
        return true;
    }

    @Override
    public String getStudentType() {
        return "Premium";
    }
}
