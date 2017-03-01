package de.scisertec.core.infrastructure.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<Enum, String> {
    private Enum annotation;

    @Override
    public void initialize(Enum annotation)
    {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext)
    {
        boolean result = false;

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if(valueForValidation == null) {
            return false;
        }

        if(enumValues != null)
        {
            for(Object enumValue:enumValues)
            {
                if(valueForValidation.equals(enumValue.toString())
                        || (this.annotation.ignoreCase() && valueForValidation.equalsIgnoreCase(enumValue.toString())))
                {
                    result = true;
                    break;
                }
            }
        }
        constraintValidatorContext.disableDefaultConstraintViolation();
        StringBuilder stringBuilder = new StringBuilder();
        if(enumValues != null) {
            for(Object o : enumValues) {
                stringBuilder.append(o.toString()).append(" ");
            }
            constraintValidatorContext.buildConstraintViolationWithTemplate("darf nur folgende Werte enthalten: "+stringBuilder.toString()).addConstraintViolation();
        }


        return result;
    }
}