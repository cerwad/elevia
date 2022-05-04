package fr.baralecorp.elevia.config.security.gcaptcha.service;

import fr.baralecorp.elevia.config.app.AppData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class DevCaptchaService implements CaptchaService {

    @Autowired
    private AppData appData;

    @Override
    public boolean verifyUserAction(String userAction, String token) {
        return true;
    }
}
