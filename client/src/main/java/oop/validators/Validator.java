package oop.validators;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Validator {

    /**
     * Checks if name in given TextField value matches regular expression.
     *
     * @param userInput text to check
     * @return -  return true, if pattern matches
     */
    public static final boolean validateUserName(final String userInput) {

        return userInput != null && userInput
                .matches("^[a-zA-Z0-9._-]{3,}$");
    }

    /**
     * Checks if name in given TextField value matches regular expression.
     *
     * @param userInput text to check
     * @return -  return true, if pattern matches
     */
    public static final boolean validatePassword(final String userInput) {

        return userInput != null && userInput.matches(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?="
                        + ".*([^a-zA-Z\\d\\s])).{9,}$");
    }


    /**
     * Hashes given string using SHA256.
     *
     * @param textToHash - text to hash
     * @return - returns hashed string
     */
    public static String hashWith256(String textToHash) {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] messageDigest = md.digest(textToHash.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);


            return hashtext;

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown"
                    + " for incorrect algorithm: " + e);

            return null;
        }
    }
}
