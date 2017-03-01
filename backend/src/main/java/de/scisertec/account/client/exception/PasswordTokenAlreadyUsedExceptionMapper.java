package de.scisertec.account.client.exception;

import de.scisertec.account.infrastructure.exception.PasswordTokenAlreadyUsedException;
import de.scisertec.core.application.api.locale.BundlePath;
import de.scisertec.core.application.api.locale.LocalePropertyManager;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;

@Provider
public class PasswordTokenAlreadyUsedExceptionMapper implements ExceptionMapper<PasswordTokenAlreadyUsedException>
{
    @Inject
    @BundlePath("validation")
    Instance<LocalePropertyManager> localePropertyManager;

    public Response toResponse(PasswordTokenAlreadyUsedException exception) {
        HashMap<String, String> response = new HashMap<>();
        response.put("error", localePropertyManager.get().getLocalizedProperty("app.base.validation.authorization.password_recovery.already_used"));
        return Response.serverError().entity(response).build();
    }
}