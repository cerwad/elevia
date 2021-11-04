package fr.baralecorp.elevia.security;

import fr.baralecorp.elevia.dao.UserRepository;
import fr.baralecorp.elevia.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class AuthenticationFailureListener implements
        ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    Logger logger = LoggerFactory.getLogger(AuthenticationFailureListener.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        String username = (String) e.getAuthentication().getPrincipal();
        if(username != null ){
            loginAttemptService.loginFailed(username);
            logger.info("Login failed for "+username+" ip: "+request.getRemoteAddr()+" nbFail: "+loginAttemptService.loginAttempts(username));
            if(loginAttemptService.isBlocked(username)){
                Optional<User> user = userRepository.findUserByEmail(username);
                if(user.isPresent() && user.get().isAccountNonLocked()) {
                    user.get().setAccountNonLocked(false);
                    userRepository.save(user.get());
                    logger.warn("Login limit reached for "+username+" ip: "+request.getRemoteAddr());
                }
            }
        }
        if (xfHeader == null) {
            loginAttemptService.loginFailed(request.getRemoteAddr());
        } else {
            loginAttemptService.loginFailed(xfHeader.split(",")[0]);
        }
    }
}