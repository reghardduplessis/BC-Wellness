package utils;

// Import required Java standard libraries
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class AuthenticateUtils {

    // Hashes the password using SHA-256
    /*
            * Hashes a given password using the SHA-256 algorithm.
     * This is used to store passwords securely in the database.
            *
            * @param password The plain text password entered by the user
     * @return A SHA-256 hashed version of the password in hexadecimal format
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not available", e);
        }
    }

    // Validates password strength
    /*
     * Checks whether the given password meets strong password criteria.
     * Criteria: Minimum 8 characters, at least 1 digit, 1 lowercase, 1 uppercase, and 1 special character.
     *
     * @param password The password entered by the user
     * @return true if the password is strong, false otherwise
     */
    public static boolean isStrongPassword(String password) {
        // Minimum 8 characters, at least 1 uppercase, 1 lowercase, 1 number, 1 special character
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=-]).{8,}$";
        return Pattern.matches(pattern, password);
    }

    // Validates email format
    public static boolean isValidEmail(String email) {
        String pattern = "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(pattern, email);
    }

    // Validates phone number (simple check for digits and length)
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10,15}");
    }
}