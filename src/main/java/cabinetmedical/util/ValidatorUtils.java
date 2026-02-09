package cabinetmedical.util;

import java.util.regex.Pattern;

public class ValidatorUtils {

    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static boolean isValidCNP(String cnp) {
        if (cnp == null) return false;
        return Pattern.matches("^\\d{13}$", cnp);
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }

    public static String getValidationErrors(String nume, String cnp, String diagnostic) {
        if (!isNotEmpty(nume)) return "Numele este obligatoriu!";
        if (!isValidCNP(cnp)) return "CNP-ul trebuie sa contina exact 13 cifre!";
        if (!isNotEmpty(diagnostic)) return "Diagnosticul este obligatoriu!";
        return null;
    }
}