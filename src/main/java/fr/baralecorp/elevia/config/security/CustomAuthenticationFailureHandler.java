package fr.baralecorp.elevia.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Autowired
    private MessageSource messages;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.warn("Authentification failure");
        super.onAuthenticationFailure(request, response, exception);


        /*String errorMessage = messages.getMessage("message.badCredentials", null, Locale.FRANCE);
        if (exception.getMessage().equalsIgnoreCase("blocked")) {
            errorMessage = messages.getMessage("auth.message.blocked", null, Locale.FRANCE);
        }**/
        String errorMessage = "Mauvais login ou mot de passe, merci de réessayer";
        if (exception.getMessage().equalsIgnoreCase("blocked")) {
            errorMessage = "Votre compte a été blocké après de trop nombreuses tentatives, merci de contacter un administrateur";
        }
    }
}