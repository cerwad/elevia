package fr.baralecorp.elevia.service.data;

import fr.baralecorp.elevia.security.CaptchaConfig;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"staging", "preprod"})
public class StagingAppData implements AppData {
    protected Env env;
    protected final CaptchaConfig captchaConfig = new CaptchaConfig();

    @Override
    public Env getEnv() {
        return env;
    }

    public void setup(String env) {
        this.env = Env.getEnv(env);
        captchaConfig.setApiKey(System.getenv("GRE_APIKEY"));
        captchaConfig.setProjectId(System.getenv("GRE_PROJECTID"));
        captchaConfig.setSiteKey(System.getenv("GRE_SITE_KEY"));
    }

    @Override
    public CaptchaConfig getCaptchaConfig() {
        return captchaConfig;
    }
}
