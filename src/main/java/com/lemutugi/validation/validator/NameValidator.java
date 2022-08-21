package com.lemutugi.validation.validator;

import com.lemutugi.validation.constraint.Name;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {
    @Override
    public void initialize(Name constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String tagline, ConstraintValidatorContext constraintValidatorContext) {
        if (tagline.trim() != null && tagline.trim() != ""){
            return tagline.length() >= 3 && tagline.length() <= 50;
        }
        return true;
    }
}
