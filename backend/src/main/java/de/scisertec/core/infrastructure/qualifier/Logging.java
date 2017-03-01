package de.scisertec.core.infrastructure.qualifier;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface Logging {

    @Nonbinding String value() default "";

    @Nonbinding boolean in() default true;

    @Nonbinding boolean out() default false;

}
