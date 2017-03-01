package de.scisertec.core.infrastructure.validation;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object>
{
    private String firstFieldName;
    private String secondFieldName;

    public void initialize(final FieldMatch constraintAnnotation)
    {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    public boolean isValid(final Object value, final ConstraintValidatorContext context)
    {
        try
        {
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);

            boolean result = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
            if(!result) {
                String message = context.getDefaultConstraintMessageTemplate();

                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addPropertyNode(firstFieldName).addConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addPropertyNode(secondFieldName).addConstraintViolation();
            }
            return result;
        }
        catch (final Exception ignore)
        {
            // ignore
        }
        return true;
    }
}