package fr.baralecorp.elevia.config.app;

import fr.baralecorp.elevia.config.security.CaptchaProperties;
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
    @Value("${google.api.key:DummyKey}")
    private String googleApiKey;

    private final CaptchaProperties captchaProperties = new CaptchaProperties();

    @Override
    public void setup(String env) {
        logger.info("Setting gcaptcha dev key " + siteKey);
        captchaProperties.setSiteKey(siteKey);
        captchaProperties.setProjectId("elevia-staging");
        captchaProperties.setApiKey(googleApiKey);
        if (googleApiKey == null || "DummyKey".equals(googleApiKey)) {
            logger.error("Did not load the google api key");
        }
    }

    @Override
    public CaptchaProperties getCaptchaConfig() {
        return captchaProperties;
    }
}
