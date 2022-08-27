package com.lemutugi.validation.constraint;


import com.lemutugi.validation.validator.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Invalid password. Password should contain minimum 8 and maximum 30 characters.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
