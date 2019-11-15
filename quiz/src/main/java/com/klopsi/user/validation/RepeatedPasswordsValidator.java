package com.klopsi.user.validation;

import com.klopsi.user.view.model.ChangePassForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * Validates if provided passwords are the same.
 *
 * @author psysiu
 */
public class RepeatedPasswordsValidator implements ConstraintValidator<RepeatedPasswords, ChangePassForm> {

    @Override
    public boolean isValid(ChangePassForm value, ConstraintValidatorContext context) {
        return Objects.equals(value.getPassword(), value.getRepeatPassword());
    }
}
