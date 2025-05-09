package com.qrcheckin.qrcheckin.Annotations;

import com.qrcheckin.qrcheckin.Validators.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Email already in use";
    boolean excludeAuthEmail() default false;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
