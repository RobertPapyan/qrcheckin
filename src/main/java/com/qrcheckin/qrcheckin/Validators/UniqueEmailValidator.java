package com.qrcheckin.qrcheckin.Validators;

import com.qrcheckin.qrcheckin.Annotations.UniqueEmail;
import com.qrcheckin.qrcheckin.Repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    private boolean excludeAuthEmail = false;

    @Autowired
    public UniqueEmailValidator (UserRepository userRepository){
        this.userRepository = userRepository;

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isBlank()) {
            return true;
        }

        if (excludeAuthEmail){
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null){
                var authEmail = auth.getName();
                if(email.equals(authEmail)){
                    return true;
                }
            }
        }

        return !userRepository.existsByEmail(email);
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        this.excludeAuthEmail = constraintAnnotation.excludeAuthEmail();
    }
}

