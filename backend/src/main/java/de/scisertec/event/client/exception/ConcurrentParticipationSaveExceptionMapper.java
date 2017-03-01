package de.scisertec.event.client.exception;

import de.scisertec.core.application.api.locale.BundlePath;
import de.scisertec.core.application.api.locale.LocalePropertyManager;
import de.scisertec.event.infrastructure.exception.ConcurrentParticipationSaveException;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;

@Provider
public class ConcurrentParticipationSaveExceptionMapper implements ExceptionMapper<ConcurrentParticipationSaveException>
{
    @Inject
    @BundlePath("validation")
    Instance<LocalePropertyManager> localePropertyManager;

    public Response toResponse(ConcurrentParticipationSaveException exception) {
        HashMap<String, String> response = new HashMap<>();
        response.put("error", localePropertyManager.get().getLocalizedProperty("app.base.validation.event.concurrent_participation_save"));
        return Response.status(Response.Status.CONFLICT).entity(response).build();
    }
}