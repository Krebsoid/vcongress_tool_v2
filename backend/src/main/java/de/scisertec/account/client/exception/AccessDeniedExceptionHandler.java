package de.scisertec.account.client.exception;

import org.apache.deltaspike.core.api.exception.control.ExceptionHandler;
import org.apache.deltaspike.core.api.exception.control.Handles;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionEvent;
import org.apache.deltaspike.security.api.authorization.AccessDeniedException;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import org.jboss.logging.Logger;

import java.util.Iterator;

@ExceptionHandler
public class AccessDeniedExceptionHandler
{
    private static final Logger logger = Logger.getLogger(AccessDeniedExceptionHandler.class.getName());

    void printExceptions(@Handles ExceptionEvent<AccessDeniedException> evt)
    {
        Iterator<SecurityViolation> iterator = evt.getException().getViolations().iterator();
        String violations = "";
        while(iterator.hasNext()) {
            SecurityViolation next = iterator.next();
            violations += next.getReason();
        }
        logger.error("Security Violation: " + violations);
        evt.throwOriginal();
    }
}