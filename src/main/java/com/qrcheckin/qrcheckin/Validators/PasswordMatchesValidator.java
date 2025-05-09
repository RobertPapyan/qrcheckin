package com.qrcheckin.qrcheckin.Validators;

import com.qrcheckin.qrcheckin.Annotations.PasswordMatches;
import com.qrcheckin.qrcheckin.Interfaces.HasMatchingPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, HasMatchingPassword> {

    @Override
    public boolean isValid(HasMatchingPassword request, ConstraintValidatorContext context) {

        if(request.getPassword() == null){
            if(request.getPassword_repeat() == null)return  true;
            return  false;
        }

        return request.getPassword().equals(request.getPassword_repeat());
    }
}
