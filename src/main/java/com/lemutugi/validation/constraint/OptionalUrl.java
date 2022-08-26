package com.lemutugi.validation.constraint;

import com.lemutugi.validation.validator.OptionalUrlValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OptionalUrlValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionalUrl {
    String message() default "Invalid url format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
