package de.scisertec.core.infrastructure.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberValidator implements ConstraintValidator<Number, Object> {

    @Override
    public void initialize(Number annotation) {
    }

    @Override
    public boolean isValid(Object valueForValidation, ConstraintValidatorContext context)
    {
        return true;
    }
}