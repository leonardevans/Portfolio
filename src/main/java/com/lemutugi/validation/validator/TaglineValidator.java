package com.lemutugi.validation.validator;

import com.lemutugi.validation.constraint.Tagline;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TaglineValidator implements ConstraintValidator<Tagline, String> {
    @Override
    public void initialize(Tagline constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String tagline, ConstraintValidatorContext constraintValidatorContext) {
        if (tagline.trim() != null && tagline.trim() != ""){
            return tagline.length() >= 3 && tagline.length() <= 35;
        }
        return true;
    }
}
