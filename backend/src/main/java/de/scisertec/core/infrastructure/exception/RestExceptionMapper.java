package de.scisertec.core.infrastructure.exception;

import org.jboss.logging.Logger;
import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ResteasyViolationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.*;

@Provider
public class RestExceptionMapper implements ExceptionMapper<ResteasyViolationException>
{
    private static final Logger logger = Logger.getLogger(RestExceptionMapper.class.getName());

    public Response toResponse(ResteasyViolationException exception) {
        ArrayList<Violation> violations = new ArrayList<>();
        for(ResteasyConstraintViolation constraintViolation : exception.getViolations()){
            Violation violation = new Violation();
            String[] strings = constraintViolation.getPath().split("[.]");
            violation.setKey(strings[strings.length-1]);
            violation.setValue(constraintViolation.getMessage());
            violations.add(violation);
        }
        Set<Violation> violationsWithoutDuplicates = removeDuplicates(violations);
        logger.info(violationsWithoutDuplicates.toString());
        return Response.serverError().entity(violationsWithoutDuplicates).build();
    }

    private Set<Violation> removeDuplicates(List<Violation> violations) {
        Set<Violation> s = new TreeSet<>((o1, o2) -> {
            return (o1.getKey() + o1.getValue()).compareTo(o2.getKey() + o2.getValue());
        });
        s.addAll(violations);
        return s;
    }

}