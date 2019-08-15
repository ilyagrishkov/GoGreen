package oop.validators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RegistrationValidator extends Validator {

    /**
     * Month with 31 days in it.
     */
    public static final int DAYS_31 = 31;

    /**
     * Month with 30 days in it.
     */
    public static final int DAYS_30 = 30;

    /**
     * Month with 29 days in it.
     */
    public static final int DAYS_29 = 29;

    /**
     * Month with 28 days in it.
     */
    public static final int DAYS_28 = 28;

    /**
     * Test variable for a leap year.
     */
    public static final int LEAP_YEAR_TEST_4 = 4;

    /**
     * Test variable for a leap year.
     */
    public static final int LEAP_YEAR_TEST_100 = 100;

    /**
     * Test variable for a leap year.
     */
    public static final int LEAP_YEAR_TEST_400 = 400;

    /**
     * List of months with 31 days in each of them.
     */
    public static final Set<String> MONTHS_WITH_31_DAYS =
            new HashSet<>(Arrays.asList("January", "March", "May",
                    "July", "August", "October", "December"));

    /**
     * List of months with 30 days in each of them.
     */
    public static final Set<String> MONTHS_WITH_30_DAYS =
            new HashSet<>(Arrays.asList("April", "June", "September",
                    "November"));


    /**
     * Checks if name in given TextField value matches regular expression.
     *
     * @param userInput text to check
     * @return -  return true, if pattern matches
     */
    public static final boolean validateName(final String userInput) {

        return userInput != null
                && userInput.matches("[a-zA-Z]+");
    }

    /**
     * Checks if password equals repassword.
     *
     * @param password   - entered password
     * @param repassword - entered repassword
     * @return - true if equals
     */
    public static final boolean checkRePassword(final String password,
                                                final String repassword) {

        return password.equals(repassword) && !password.equals("");
    }


    /**
     * Checks how many days are in the month/year, picked by a user.
     *
     * @param month - month to check
     * @param year  - year to check
     * @return - returns number of days in chose month/year
     */
    public static final int validateNumberOfDays(final String month, final int year) {

        if (MONTHS_WITH_31_DAYS.contains(month)) {

            return DAYS_31;

        } else if (MONTHS_WITH_30_DAYS.contains(month)) {

            return DAYS_30;

        } else {

            if (((year % LEAP_YEAR_TEST_4 == 0)
                    && (year % LEAP_YEAR_TEST_100 != 0))
                    || (year % LEAP_YEAR_TEST_400 == 0)) {

                return DAYS_29;

            } else {

                return DAYS_28;
            }
        }
    }


}
