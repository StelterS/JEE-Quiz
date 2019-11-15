package com.klopsi.user.validation;

import com.klopsi.user.UserService;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class LoginValidator implements ConstraintValidator<UniqueLogin, String> {
	private UserService service;

	@Inject
	public LoginValidator(UserService service){
		this.service = service;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		List<String> logins = service.findUserLogins();
		return !logins.contains(value);
	}
}
