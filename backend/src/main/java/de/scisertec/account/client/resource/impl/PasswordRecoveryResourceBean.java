package de.scisertec.account.client.resource.impl;

import de.scisertec.account.application.api.service.PasswordRecovery;
import de.scisertec.account.client.model.request.ExecutePasswordRecoveryRequest;
import de.scisertec.account.client.model.request.InitializePasswordRecoveryRequest;
import de.scisertec.account.client.resource.api.PasswordRecoveryResource;
import de.scisertec.core.infrastructure.qualifier.Logging;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

@ApplicationScoped
public class PasswordRecoveryResourceBean implements PasswordRecoveryResource {

    @Inject
    PasswordRecovery passwordRecovery;

    @Logging
    public void initPasswordChange(@Valid InitializePasswordRecoveryRequest request) {
        passwordRecovery.initiatePasswordChange(request);
    }

    @Logging
    public void executeChangePassword(@Valid ExecutePasswordRecoveryRequest request) {
        passwordRecovery.changePassword(request);
    }
}
