package fr.baralecorp.elevia.service.data;

import fr.baralecorp.elevia.security.CaptchaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevAppData implements AppData {
    Logger logger = LoggerFactory.getLogger(DevAppData.class);
    protected Env env;
    @Value("${gre.sitekey}")
    private String siteKey;

    private final CaptchaConfig captchaConfig = new CaptchaConfig();

    @Override
    public void setup(String env) {
        logger.info("Setting gcaptcha dev key " + siteKey);
        captchaConfig.setSiteKey(siteKey);
    }

    @Override
    public CaptchaConfig getCaptchaConfig() {
        return captchaConfig;
    }
}
