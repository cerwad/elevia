package fr.baralecorp.elevia.config.security.controller.session;

import fr.baralecorp.elevia.domain.User;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    boolean isUserAuthenticated();

    User getAuthenticatedUser();
}
