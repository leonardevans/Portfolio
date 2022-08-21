package com.lemutugi.validation.validator;

import com.lemutugi.validation.constraint.ShortDescription;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ShortDescriptionValidator implements ConstraintValidator<ShortDescription, String> {
    @Override
    public void initialize(ShortDescription constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String shortDescription, ConstraintValidatorContext constraintValidatorContext) {
        if (shortDescription != null && shortDescription.trim() != ""){
            return shortDescription.length() > 49 && shortDescription.length() < 301;
        }
        return true;
    }
}
