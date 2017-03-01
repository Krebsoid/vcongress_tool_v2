package de.scisertec.event.client.exception;

import de.scisertec.core.application.api.locale.BundlePath;
import de.scisertec.core.application.api.locale.LocalePropertyManager;
import de.scisertec.event.infrastructure.exception.IncompleteParticipationException;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;

@Provider
public class IncompleteParticipationExceptionMapper implements ExceptionMapper<IncompleteParticipationException>
{
    @Inject
    @BundlePath("validation")
    Instance<LocalePropertyManager> localePropertyManager;

    public Response toResponse(IncompleteParticipationException exception) {
        HashMap<String, String> response = new HashMap<>();
        response.put("error", localePropertyManager.get().getLocalizedProperty("app.base.validation.event.incomplete_participation"));
        return Response.status(Response.Status.CONFLICT).entity(response).build();
    }
}