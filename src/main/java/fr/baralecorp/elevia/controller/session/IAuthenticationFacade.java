package fr.baralecorp.elevia.controller.session;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    boolean isUserAuthenticated();
}
