package de.scisertec.account.client.exception;

import de.scisertec.account.infrastructure.exception.AlreadyLoggedInException;
import de.scisertec.core.application.api.locale.BundlePath;
import de.scisertec.core.application.api.locale.LocalePropertyManager;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.text.MessageFormat;
import java.util.HashMap;

@Provider
public class AlreadyLoggedInExceptionMapper implements ExceptionMapper<AlreadyLoggedInException>
{
    @Inject
    @BundlePath("validation")
    Instance<LocalePropertyManager> localePropertyManager;

    public Response toResponse(AlreadyLoggedInException exception) {
        String message = localePropertyManager.get().getLocalizedProperty("app.base.validation.authorization.login.already_logged_in");
        String filledMessage = MessageFormat.format(message, exception.getUsername());

        HashMap<String, String> response = new HashMap<>();
        response.put("error", filledMessage);
        return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
    }
}