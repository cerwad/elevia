package fr.baralecorp.elevia.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import fr.baralecorp.elevia.security.gcaptcha.Assesment;
import fr.baralecorp.elevia.security.gcaptcha.Event;
import fr.baralecorp.elevia.service.data.AppData;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CaptchaServiceImplTest {
    Logger logger = LoggerFactory.getLogger(CaptchaServiceImplTest.class);

    @Mock
    RestTemplate restTemplate;
    @Mock
    AppData appData;
    @InjectMocks
    @Spy
    CaptchaServiceImpl service;
    private String captchaUrl = "https://google-api/recaptcha/";
    private float minScore = 0.5f;
    String token = "TOKEN";
    String userAction = "LOGIN";
    Event event = new Event();

    CaptchaProperties captchaProperties = new CaptchaProperties("site-key", "project-id", "api-key");

    @BeforeEach
    public void init() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        service.captchaUrl = captchaUrl;
        service.minScore = minScore;

        event.setSiteKey(captchaProperties.getSiteKey());
        event.setToken(token);
        event.setExpectedAction(userAction);

        JSONPObject eventJsonObject = new JSONPObject("event", event);
        HttpEntity<String> request =
                new HttpEntity<String>(eventJsonObject.toString(), headers);
    }

    @Test
    public void testEventNull() throws Exception {

        when(appData.getCaptchaConfig()).thenReturn(captchaProperties);
        when(restTemplate.postForObject(eq(buildCaptchaUrlEnd()), any(HttpEntity.class), eq(Assesment.class)))
                .thenReturn(new Assesment());
        assertThrows(SecurityException.class, () -> {
            service.verifyUserAction(userAction, token);
        });
    }

    @Test
    public void testEventNotEquals() throws Exception {
        Event event = new Event();
        event.setSiteKey(captchaProperties.getSiteKey());
        event.setToken(token);
        event.setExpectedAction("SIGNUP");

        Assesment assesment = new Assesment();
        assesment.setEvent(event);
        assesment.setName("Assesment num 1");
        assesment.setScore(0.7f);

        when(appData.getCaptchaConfig()).thenReturn(captchaProperties);
        when(restTemplate.postForObject(eq(buildCaptchaUrlEnd()), any(HttpEntity.class), eq(Assesment.class)))
                .thenReturn(assesment);
        assertThrows(SecurityException.class, () -> {
            service.verifyUserAction(userAction, token);
        });
    }

    @Test
    public void testBadMark() throws Exception {
        Assesment assesment = new Assesment();
        assesment.setEvent(event);
        assesment.setName("Assesment num 1");
        assesment.setScore(0.4f);

        when(appData.getCaptchaConfig()).thenReturn(captchaProperties);
        when(restTemplate.postForObject(eq(buildCaptchaUrlEnd()), any(HttpEntity.class), eq(Assesment.class)))
                .thenReturn(assesment);
        assertThrows(RuntimeException.class, () -> {
            service.verifyUserAction(userAction, token);
        });
    }

    @Test
    public void testAssesmentOk() throws Exception {
        Assesment assesment = new Assesment();
        assesment.setEvent(event);
        assesment.setName("Assesment num 1");
        assesment.setScore(0.7f);

        when(appData.getCaptchaConfig()).thenReturn(captchaProperties);
        when(restTemplate.postForObject(eq(buildCaptchaUrlEnd()), any(HttpEntity.class), eq(Assesment.class)))
                .thenReturn(assesment);
        boolean verif = service.verifyUserAction(userAction, token);
        assertTrue(verif);
    }

    @Test
    public void testJsonPayload() throws JsonProcessingException, JSONException {
        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = String.format("{\n" +
                "  \"event\": {\n" +
                "    \"token\": \"%s\",\n" +
                "    \"siteKey\": \"%s\",\n" +
                "    \"expectedAction\": \"%s\"\n" +
                "  }\n" +
                "}", token, captchaProperties.getSiteKey(), userAction);
        String jsonString = mapper.writeValueAsString(event);
        logger.debug(jsonString);

        JSONAssert.assertEquals(expectedJson, jsonString, false);
    }

    protected String buildCaptchaUrlEnd() {
        return captchaUrl + captchaProperties.getProjectId() + "/assessments?key=" + captchaProperties.getApiKey();
    }
}