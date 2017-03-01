package de.scisertec.account.infrastructure.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {

    String message() default "{de.scisertec.constraints.UniqueEmail.message}";

    boolean omitSelf() default false;
    String group() default "system";
    String email();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
