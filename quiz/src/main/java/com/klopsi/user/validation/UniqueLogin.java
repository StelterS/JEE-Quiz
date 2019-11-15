package com.klopsi.user.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LoginValidator.class)
@Documented
public @interface UniqueLogin {
	String message() default "Login must be unique";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
