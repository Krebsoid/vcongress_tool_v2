package de.scisertec.account.client.resource.impl;

import de.scisertec.account.application.api.model.representation.UserStateRepresentation;
import de.scisertec.account.application.api.security.LoggedIn;
import de.scisertec.account.application.api.service.Entrance;
import de.scisertec.account.client.model.request.LoginRequest;
import de.scisertec.account.client.resource.api.AuthorizationResource;
import de.scisertec.core.infrastructure.qualifier.Logging;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;

@ApplicationScoped
public class AuthorizationResourceBean implements AuthorizationResource {

    @Inject
    Entrance entrance;

    @Logging(out = true)
    public UserStateRepresentation login(@Valid LoginRequest loginRequest) {
        return entrance.login(loginRequest);
    }

    @Override
    public UserStateRepresentation autoLogin(String autoLoginToken) {
        return entrance.autoLogin(autoLoginToken);
    }

    @Override
    public UserStateRepresentation remoteLogin(String remoteLoginToken) {
        return entrance.remoteLogin(remoteLoginToken);
    }

    @Override
    public String getRemoteLoginLink() {
        return entrance.getRemoteLoginLink();
    }

    @LoggedIn
    @Logging(out = true)
    public void logout() {
        entrance.logout();
    }

    @Logging(out = true)
    public UserStateRepresentation state() {
        return entrance.activeState();
    }

}
