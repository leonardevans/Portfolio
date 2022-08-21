package com.lemutugi.validation.validator;

import com.lemutugi.validation.constraint.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, Long> {
    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (phoneNumber != null){
            String phoneNumberString = phoneNumber.toString();
            return phoneNumberString.matches("[0-9]+") && (phoneNumberString.length() > 8) && (phoneNumberString.length() < 14);
        }
        return true;
    }
}
