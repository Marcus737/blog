package com.wei.blogservice.validator;

import com.wei.blogservice.annotations.ValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private boolean isUpper(char c){
        return c >= 'A' && c <= 'Z';
    }

    private boolean isLower(char c){
        return c >= 'a' && c <= 'z';
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean upper = false, lower = false;
        for (int i = 0; i < value.length(); i++) {
            if (!upper) upper = isUpper(value.charAt(i));
            if(!lower) lower = isLower(value.charAt(i));
        }
        return upper && lower;
    }
}
