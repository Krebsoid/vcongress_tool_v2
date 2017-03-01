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
@Constraint(validatedBy = ExistingEmailValidator.class)
public @interface ExistingEmail {

    String message() default "{de.scisertec.constraints.ExistingEmail.message}";

    String group();
    String email();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
