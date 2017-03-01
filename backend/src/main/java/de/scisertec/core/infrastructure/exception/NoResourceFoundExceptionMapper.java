package de.scisertec.core.infrastructure.exception;

import de.scisertec.core.infrastructure.qualifier.Active;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Locale;

@Provider
public class NoResourceFoundExceptionMapper implements ExceptionMapper<NoResourceFoundException>
{
    HashMap<String, String> localizedErrorMap = new HashMap<String, String>();

    @Inject
    @Active
    Instance<Locale> localeInstance;

    public Response toResponse(NoResourceFoundException exception) {
        initializeErrorMap();
        String message = String.format(localizedErrorMap.get(localeInstance.get().getLanguage()), exception.clazz.getSimpleName(), exception.getId());
        HashMap<String, String> response = new HashMap<>();
        response.put("error", message);
        return Response.status(Response.Status.GONE).entity(response).build();
    }

    public void initializeErrorMap() {
        localizedErrorMap.put("de", "Ressource vom Typ: %s mit der ID: %s nicht existent!");
        localizedErrorMap.put("en", "Resource from type: %s with ID: %s not existent!");
    }
}