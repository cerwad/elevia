package fr.baralecorp.elevia.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.baralecorp.elevia.security.gcaptcha.Assesment;
import fr.baralecorp.elevia.security.gcaptcha.Event;
import fr.baralecorp.elevia.service.data.AppData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Component
@Profile({"staging", "preprod", "dev"})
public class CaptchaServiceImpl implements CaptchaService {

    Logger logger = LoggerFactory.getLogger(CaptchaServiceImpl.class);

    @Autowired
    private AppData appData;

    @Value("${grecaptcha.url}")
    protected String captchaUrl;
    @Value("${grecaptcha.min.score}")
    protected float minScore;
    @Autowired
    private RestTemplate restTemplate;
    final private HttpHeaders headers = new HttpHeaders();

    @PostConstruct
    protected void init() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public boolean verifyUserAction(String userAction, String token) {
        logger.info("Call to google api with token " + token + " action " + userAction);
        Event event = new Event();
        event.setSiteKey(appData.getCaptchaConfig().getSiteKey());
        event.setToken(token);
        event.setExpectedAction(userAction);

        ObjectMapper mapper = new ObjectMapper();
        HttpEntity<String> request =
                null;
        try {
            request = new HttpEntity<String>(mapper.writeValueAsString(event), headers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while parsing event into json", e);
        }
        // Call google APIs
        logger.debug("Asking for an assessment at url: " + buildCaptchaUrlEnd());
        Assesment assesment = restTemplate.postForObject(buildCaptchaUrlEnd(), request, Assesment.class);
        if (assesment == null) {
            throw new RuntimeException("Google recaptcha sent an empty assesment, should never happen");
        }
        verifyEvent(event, assesment);
        if (assesment.getScore() < minScore) {
            logger.warn("Google detected a fraudulent behavior with a score of " + assesment.getScore() + " from this user for reasons: " + Arrays.toString(assesment.getReasons()));
            throw new RuntimeException("Are you a robot ? Google thinks so");
        }
        logger.debug("Google passed this request with a score of " + assesment.getScore());
        return true;
    }

    private void verifyEvent(@NotNull Event originalEvent, @NotNull Assesment assesment) {
        if (!originalEvent.equals(assesment.getEvent())) {
            String eventStr = assesment.getEvent() == null ? "null" : assesment.getEvent().toString();
            throw new SecurityException("Google Recaptcha Event received do not match with event sent: " + eventStr);
        }
    }

    protected String buildCaptchaUrlEnd() {
        return captchaUrl + appData.getCaptchaConfig().getProjectId() + "/assessments?key=" + appData.getCaptchaConfig().getApiKey();
    }
}
