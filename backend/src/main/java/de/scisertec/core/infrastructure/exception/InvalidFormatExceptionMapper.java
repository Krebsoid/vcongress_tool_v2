package de.scisertec.core.infrastructure.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidFormatExceptionMapper implements ExceptionMapper<InvalidFormatException>
{

    public Response toResponse(InvalidFormatException exception) {
        Violation violation = new Violation();
        JsonMappingException.Reference s = exception.getPath().get(0);
        violation.setKey(s.getFieldName());
        violation.setValue(exception.getMessage());
        return Response.serverError().entity(violation).build();
    }

}