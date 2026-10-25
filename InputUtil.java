package util;

import exception.InvalidInputException;
import java.util.Scanner;

/**
 * Console input helper. Every reader keeps asking until the user types a
 * valid value, so bad input can never crash the program.
 */
public final class InputUtil {

    private static final Scanner SCANNER = new Scanner(System.in);

    private InputUtil() {
    }

    /** Reads one raw trimmed line. */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine().trim();
    }

    public static String readName(String prompt) {
        while (true) {
            try {
                return ValidationUtil.validateName(readLine(prompt));
            } catch (InvalidInputException e) {
                System.out.println("  [Invalid] " + e.getMessage());
            }
        }
    }

    public static String readTitle(String prompt) {
        while (true) {
            try {
                return ValidationUtil.validateTitle(readLine(prompt));
            } catch (InvalidInputException e) {
                System.out.println("  [Invalid] " + e.getMessage());
            }
        }
    }

    public static String readEmail(String prompt) {
        while (true) {
            try {
                return ValidationUtil.validateEmail(readLine(prompt));
            } catch (InvalidInputException e) {
                System.out.println("  [Invalid] " + e.getMessage());
            }
        }
    }

    public static int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                return ValidationUtil.parseInt(readLine(prompt), min, max);
            } catch (InvalidInputException e) {
                System.out.println("  [Invalid] " + e.getMessage());
            }
        }
    }

    public static double readDouble(String prompt, double min, double max) {
        while (true) {
            try {
                return ValidationUtil.parseDouble(readLine(prompt), min, max);
            } catch (InvalidInputException e) {
                System.out.println("  [Invalid] " + e.getMessage());
            }
        }
    }

    /** Reads an ID such as CRS101 (letters + digits only). */
    public static String readId(String prompt) {
        while (true) {
            String text = readLine(prompt).toUpperCase();
            if (text.matches("^[A-Z]{3}\\d{3,4}$")) {
                return text;
            }
            System.out.println("  [Invalid] ID format is 3 letters + digits (e.g. CRS101, STU1001).");
        }
    }
}
