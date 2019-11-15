package com.klopsi.user.validation;

import com.klopsi.user.view.model.RegistrationForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class RepeatedPasswordRegisterValidator implements ConstraintValidator<RepeatedPasswords, RegistrationForm> {

	@Override
	public boolean isValid(RegistrationForm value, ConstraintValidatorContext context) {
		return Objects.equals(value.getPassword(), value.getRepeatPassword());
	}
}
