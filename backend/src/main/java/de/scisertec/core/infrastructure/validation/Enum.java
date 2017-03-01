package de.scisertec.core.infrastructure.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {EnumValueValidator.class})
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Enum
{
    String message() default "Invalid value. This is not permitted.";

    Class[] groups() default {};

    Class[] payload() default {};

    Class enumClass();

    boolean ignoreCase() default false;
}