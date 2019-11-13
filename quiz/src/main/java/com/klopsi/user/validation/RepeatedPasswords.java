package com.klopsi.user.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

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
