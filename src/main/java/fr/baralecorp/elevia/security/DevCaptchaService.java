package fr.baralecorp.elevia.security;

import fr.baralecorp.elevia.service.data.AppData;
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
