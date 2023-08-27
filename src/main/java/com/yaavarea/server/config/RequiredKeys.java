package com.yaavarea.server.config;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequiredKeysValidator.class)
public @interface RequiredKeys {

    String[] value();

    String message() default "Missing required keys";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class RequiredKeysValidator implements ConstraintValidator<RequiredKeys, List<Map<String, String>>> {

    private String[] requiredKeys;

    @Override
    public void initialize(RequiredKeys constraintAnnotation) {
        requiredKeys = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<Map<String, String>> value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        for (Map<String, String> map : value) {
            if (!hasRequiredKeys(map)) {
                return false;
            }
        }

        return true;
    }

    private boolean hasRequiredKeys(Map<String, String> map) {
        for (String key : requiredKeys) {
            if (!map.containsKey(key)) {
                return false;
            }
        }
        return true;
    }
}


