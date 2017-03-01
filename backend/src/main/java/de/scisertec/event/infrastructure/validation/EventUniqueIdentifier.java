package de.scisertec.event.infrastructure.validation;

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
@Constraint(validatedBy = EventUniqueIdentifierValidator.class)
public @interface EventUniqueIdentifier {

    String message() default "{de.scisertec.constraints.UniqueEmail.message}";

    boolean omitSelf() default false;
    String event() default "0";
    String identifier();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
