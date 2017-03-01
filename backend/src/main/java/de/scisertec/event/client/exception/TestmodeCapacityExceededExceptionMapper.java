package de.scisertec.event.client.exception;

import de.scisertec.core.application.api.locale.BundlePath;
import de.scisertec.core.application.api.locale.LocalePropertyManager;
import de.scisertec.event.infrastructure.exception.IncompleteParticipationException;
import de.scisertec.event.infrastructure.exception.TestmodeCapacityExceededException;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;

@Provider
public class TestmodeCapacityExceededExceptionMapper implements ExceptionMapper<TestmodeCapacityExceededException>
{
    @Inject
    @BundlePath("validation")
    Instance<LocalePropertyManager> localePropertyManager;

    public Response toResponse(TestmodeCapacityExceededException exception) {
        HashMap<String, String> response = new HashMap<>();
        response.put("error", localePropertyManager.get().getLocalizedProperty("app.base.validation.event.testmode_capacity_exceeded"));
        return Response.status(Response.Status.CONFLICT).entity(response).build();
    }
}