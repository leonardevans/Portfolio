package com.lemutugi.validation.constraint;

import com.lemutugi.validation.validator.TaglineValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TaglineValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Tagline {
    String message() default "Invalid tagline";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
