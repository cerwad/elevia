package fr.baralecorp.elevia.service.data;

import fr.baralecorp.elevia.security.CaptchaProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"staging", "preprod"})
public class StagingAppData implements AppData {
    protected Env env;
    protected final CaptchaProperties captchaProperties = new CaptchaProperties();

    @Override
    public Env getEnv() {
        return env;
    }

    public void setup(String env) {
        this.env = Env.getEnv(env);
        captchaProperties.setApiKey(System.getenv("GRE_APIKEY"));
        captchaProperties.setProjectId(System.getenv("GRE_PROJECTID"));
        captchaProperties.setSiteKey(System.getenv("GRE_SITE_KEY"));
    }

    @Override
    public CaptchaProperties getCaptchaConfig() {
        return captchaProperties;
    }
}
