package fr.baralecorp.elevia.config.app;

import fr.baralecorp.elevia.config.security.CaptchaProperties;

public interface AppData {

    default Env getEnv() {
        return Env.DEV;
    }

    void setup(String env);

    CaptchaProperties getCaptchaConfig();

    default String getAppLoggingLevel() {
        return System.getenv("LOGGING_APP_LEVEL");
    }

    default String getSpringLoggingLevel() {
        return System.getenv("LOGGING_SPRING_LEVEL");
    }

    default String getRootLoggingLevel() {
        return System.getenv("LOGGING_ROOT_LEVEL");
    }
}
