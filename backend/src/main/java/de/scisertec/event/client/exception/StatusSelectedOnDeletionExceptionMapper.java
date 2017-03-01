package de.scisertec.event.client.exception;

import de.scisertec.core.application.api.locale.BundlePath;
import de.scisertec.core.application.api.locale.LocalePropertyManager;
import de.scisertec.event.infrastructure.exception.StatusSelectedOnDeletionException;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;

@Provider
public class StatusSelectedOnDeletionExceptionMapper implements ExceptionMapper<StatusSelectedOnDeletionException>
{
    @Inject
    @BundlePath("validation")
    Instance<LocalePropertyManager> localePropertyManager;

    public Response toResponse(StatusSelectedOnDeletionException exception) {
        HashMap<String, String> response = new HashMap<>();
        response.put("error", localePropertyManager.get().getLocalizedProperty("app.base.validation.event.status_deletion_on_selected"));
        return Response.status(Response.Status.CONFLICT).entity(response).build();
    }
}