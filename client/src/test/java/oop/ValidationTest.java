package oop;

import oop.validators.RegistrationValidator;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


public class ValidationTest {


    //Testing day count checks
    @Test
    public void test_validateTNumberOfDays_31() {

        assertThat(RegistrationValidator
                .validateNumberOfDays("January", 1970), equalTo(31));
    }

    @Test
    public void test_validateTNumberOfDays_30() {

        assertThat(RegistrationValidator
                .validateNumberOfDays("April", 1970), equalTo(30));
    }

    @Test
    public void test_validateTNumberOfDays_29() {

        assertThat(RegistrationValidator
                .validateNumberOfDays("February", 2004), equalTo(29));
    }

    @Test
    public void test_validateTNumberOfDays_28() {

        assertThat(RegistrationValidator
                .validateNumberOfDays("February", 1970), equalTo(28));
    }

    @Test
    public void test_validateTNumberOfDays_28_nonLeapYear() {

        assertThat(RegistrationValidator
                .validateNumberOfDays("February", 1900), equalTo(28));
    }

    @Test
    public void test_validateTNumberOfDays_29_LeapYear() {

        assertThat(RegistrationValidator
                .validateNumberOfDays("February", 2000), equalTo(29));
    }


    //Testing validators
    @Test
    public void test_validateUserName_wrong() {

        assertThat(RegistrationValidator
                .validateUserName("ab"), equalTo(false));
    }

    @Test
    public void test_validateUserName_null() {

        assertThat(RegistrationValidator
                .validateUserName(null), equalTo(false));
    }

    @Test
    public void test_validateUserName_correct() {

        assertThat(RegistrationValidator
                .validateUserName("abc"), equalTo(true));
    }

    @Test
    public void test_validateName_wrong() {

        assertThat(RegistrationValidator
                .validateName("ab2"), equalTo(false));
    }

    @Test
    public void test_validateName_null() {

        assertThat(RegistrationValidator
                .validateName(null), equalTo(false));
    }

    @Test
    public void test_validateName_correct() {

        assertThat(RegistrationValidator
                .validateName("abc"), equalTo(true));
    }

    @Test
    public void test_validatePassword_wrong() {

        assertThat(RegistrationValidator
                .validatePassword("abcadf43dda"), equalTo(false));
    }

    @Test
    public void test_validatePassword_null() {

        assertThat(RegistrationValidator
                .validatePassword(null), equalTo(false));
    }

    @Test
    public void test_validatePassword_correct() {

        assertThat(RegistrationValidator
                .validatePassword("Password123!"), equalTo(true));
    }


    //Testing rePassword comparison
    @Test
    public void test_checkRePassword_wrong_different_pass() {

        assertThat(RegistrationValidator
                .checkRePassword("pass1", "pass2"), equalTo(false));
    }

    @Test
    public void test_checkRePassword_wrong_empty_different_pass() {

        assertThat(RegistrationValidator
                .checkRePassword("", "pass"), equalTo(false));
    }

    @Test
    public void test_checkRePassword_wrong_empty__same_pass() {

        assertThat(RegistrationValidator
                .checkRePassword("", ""), equalTo(false));
    }

    @Test
    public void test_checkRePassword_correct() {

        assertThat(RegistrationValidator
                .checkRePassword("pass1", "pass1"), equalTo(true));
    }

    //Testing hasing
    @Test
    public void test_hashing() {

        assertThat(RegistrationValidator.hashWith256("test"), equalTo(
                "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08"
        ));
    }


}
