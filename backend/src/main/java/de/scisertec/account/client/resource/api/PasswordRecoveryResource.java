package de.scisertec.account.client.resource.api;

import de.scisertec.account.client.model.request.ExecutePasswordRecoveryRequest;
import de.scisertec.account.client.model.request.InitializePasswordRecoveryRequest;
import de.scisertec.core.client.resource.api.Resource;
import de.scisertec.core.infrastructure.qualifier.Logging;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("account/password/change")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PasswordRecoveryResource extends Resource {

    @Path("init")
    @POST
    void initPasswordChange(@Valid InitializePasswordRecoveryRequest request);

    @POST
    void executeChangePassword(@Valid ExecutePasswordRecoveryRequest request);

}
