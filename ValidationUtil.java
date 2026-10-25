package util;

import exception.InvalidInputException;

/**
 * Pure validation helpers. Each method returns the cleaned value or throws
 * an InvalidInputException describing what is wrong.
 */
public final class ValidationUtil {

    private static final String NAME_REGEX  = "^[A-Za-z][A-Za-z .'-]{1,39}$";
    private static final String TITLE_REGEX = "^[A-Za-z0-9][A-Za-z0-9 +#&.,'-]{2,49}$";
    private static final String EMAIL_REGEX = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$";

    private ValidationUtil() {
    }

    public static String validateName(String text) throws InvalidInputException {
        if (text == null || text.isBlank()) {
            throw new InvalidInputException("Name cannot be empty.");
        }
        if (!text.matches(NAME_REGEX)) {
            throw new InvalidInputException(
                    "Name must be 2-40 characters (letters, spaces, '.', '-' only).");
        }
        return text;
    }

    public static String validateTitle(String text) throws InvalidInputException {
        if (text == null || text.isBlank()) {
            throw new InvalidInputException("Value cannot be empty.");
        }
        if (!text.matches(TITLE_REGEX)) {
            throw new InvalidInputException(
                    "Must be 3-50 characters (letters, digits, spaces and + # & . , - only).");
        }
        return text;
    }

    public static String validateEmail(String text) throws InvalidInputException {
        if (text == null || !text.matches(EMAIL_REGEX)) {
            throw new InvalidInputException("Invalid e-mail format (example: name@example.com).");
        }
        return text.toLowerCase();
    }

    public static int parseInt(String text, int min, int max) throws InvalidInputException {
        int value;
        try {
            value = Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("'" + text + "' is not a valid whole number.");
        }
        if (value < min || value > max) {
            throw new InvalidInputException("Value must be between " + min + " and " + max + ".");
        }
        return value;
    }

    public static double parseDouble(String text, double min, double max) throws InvalidInputException {
        double value;
        try {
            value = Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("'" + text + "' is not a valid amount.");
        }
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new InvalidInputException("Amount is not valid.");
        }
        if (value < min || value > max) {
            throw new InvalidInputException(
                    String.format("Amount must be between %.2f and %.2f.", min, max));
        }
        return value;
    }
}
