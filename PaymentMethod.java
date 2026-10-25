package model;

/**
 * Supported payment methods.
 */
public enum PaymentMethod {
    CASH("Cash"),
    CARD("Credit/Debit Card"),
    UPI("UPI"),
    NET_BANKING("Net Banking");

    private final String label;

    PaymentMethod(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
