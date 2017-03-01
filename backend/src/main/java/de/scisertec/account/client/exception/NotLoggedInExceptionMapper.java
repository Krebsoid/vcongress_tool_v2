package de.scisertec.account.client.exception;

import de.scisertec.account.infrastructure.exception.NotLoggedInException;
import de.scisertec.core.application.api.locale.BundlePath;
import de.scisertec.core.application.api.locale.LocalePropertyManager;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;

@Provider
public class NotLoggedInExceptionMapper implements ExceptionMapper<NotLoggedInException>
{
    @Inject
    @BundlePath("validation")
    Instance<LocalePropertyManager> localePropertyManager;

    public Response toResponse(NotLoggedInException exception) {
        HashMap<String, String> response = new HashMap<>();
        response.put("error", localePropertyManager.get().getLocalizedProperty("app.base.validation.authorization.login.not_logged_in"));
        return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
    }
}