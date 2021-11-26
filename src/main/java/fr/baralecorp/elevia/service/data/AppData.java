package fr.baralecorp.elevia.service.data;

import fr.baralecorp.elevia.security.CaptchaConfig;

public interface AppData {

    default Env getEnv() {
        return Env.DEV;
    }

    void setup(String env);

    CaptchaConfig getCaptchaConfig();

}
