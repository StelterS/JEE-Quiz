package com.klopsi.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates if password contains at least one small letter, one capital letter and one digit.
 *
 * @author psysiu
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean capital = false;
        boolean small = false;
        boolean digit = false;

        for (char c : value.toCharArray()) {
            if (Character.isUpperCase(c)) {
                capital = true;
            }
            if (Character.isLowerCase(c)) {
                small = true;
            }
            if (Character.isDigit(c)) {
                digit = true;
            }
        }
        return capital && small && digit;
    }

}
