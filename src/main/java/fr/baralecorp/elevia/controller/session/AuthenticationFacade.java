package fr.baralecorp.elevia.controller.session;

import fr.baralecorp.elevia.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated() && authentication.getPrincipal() instanceof User;
    }

    @Override
    public User getAuthenticatedUser() {
        User u = null;
        if (isUserAuthenticated()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            u = (User) authentication.getPrincipal();
        }
        return u;
    }
}