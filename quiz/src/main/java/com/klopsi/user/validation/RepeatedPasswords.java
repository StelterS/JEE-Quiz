package com.klopsi.user.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Assigns password validator to field.
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RepeatedPasswordsValidator.class)
@Documented
public @interface RepeatedPasswords {

    String message() default "passwords must be the same";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
