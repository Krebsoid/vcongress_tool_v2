package de.scisertec.account.application.api.security;

import org.apache.deltaspike.security.api.authorization.SecurityBindingType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@SecurityBindingType
@Documented
@Target({FIELD, METHOD, TYPE, PARAMETER})
@Retention(RUNTIME)
public @interface UserRestricted {
}