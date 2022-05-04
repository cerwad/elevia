package fr.baralecorp.elevia.config.security.gcaptcha.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import fr.baralecorp.elevia.config.app.AppData;
import fr.baralecorp.elevia.config.security.CaptchaProperties;
import fr.baralecorp.elevia.config.security.gcaptcha.Assesment;
import fr.baralecorp.elevia.config.security.gcaptcha.Event;
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

import static org.junit.jupiter.api.Assertions.*;
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
    public void testJsonPayload() throws JSONException {
        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = String.format("{\n" +
                "  \"event\": {\n" +
                "    \"token\": \"%s\",\n" +
                "    \"siteKey\": \"%s\",\n" +
                "    \"expectedAction\": \"%s\"\n" +
                "  }\n" +
                "}", token, captchaProperties.getSiteKey(), userAction);

        String jsonString = CaptchaServiceImpl.convertEventToJson(event);
        logger.debug(jsonString);

        JSONAssert.assertEquals(expectedJson, jsonString, false);
    }

    protected String buildCaptchaUrlEnd() {
        return captchaUrl + captchaProperties.getProjectId() + "/assessments?key=" + captchaProperties.getApiKey();
    }

    @Test
    public void testResponseParsing() throws JsonProcessingException {
        String responseBody = "{\n" +
                "  \"name\": \"projects/486622645797/assessments/9893b44998000000\",\n" +
                "  \"event\": {\n" +
                "    \"token\": \"03AGdBq24pMoA5rctM8V1x757Dcm03PkU47UJ2ryvFYJtYXYQM2hu-COvAdFufMgDJ251lV3HWS4yNtBPJVAU2Q4er5uvAWVpEVYJETXsmobAj-Qg34mHtbBvp90nNLoUtk-ZbefR9-Ft3VdJMF9tvULmHYTTP5jf0gIb0vU6jO1ocrz-pY8_AFN-xCWvWnZngHB-tllLbTfeFnlazP9kI3BSyMaEaVv4IVqIjQIwGr8tnRz90OYnQ4Gl3lXlEdxj-4OOMVyXouy2ZxYcWyS5KgK4niXA5-xVHj2XchYGDILQ4VoiWgeyePK-0hl5TwOB7HbifN9wU6-6w1yTo80VcpYCeOd6Zhyv09XwuFvlKjZmJoS4P5pjMWHdwQbb_93HWXP8sgMdBJIAHiJscm6vn5OnfJuoGfG_7FxSKbeWFx1BPwU4raxmSnYiXe0wBPtGUsK0A7DyjDhTektkTylaoLiebYhTPxWCAGJAQ2LMhMGjJdOodXxxMNzap1MslV7mIOJK5ki_FD0AyW1F3tfusQkyHJ3YK9LLxYk1aeZEgLT8qjRBLXpvvJPcmxIF5n5Wi5En3fD1AtqX3mj2Gsm-H2K2bJYPCo-VRAmBfxUFUC694HVNdhfCAeLemJHrGaJ9LuWD0PJ7acZLFrzPEJB9tJCOltMVra5Kn4FaxfDNixVEmAw5ahvppT5mKcApKHP_HVjcrWYRq8cVohphYPRebh3mTDiCWIF-RQFe9jRA3lRkbN60PSuqzdaFbfY8358LJTpiXVoyMXw380MsaHRofcCczAOV8GBjAadX_4nKRbv4QmcjMDy53GVmrALfHLN7OkRc7sJmBB29iCFmHVva_YYd9ucrUNK4zO2qxCXCyWPUvd2yGy-jCMqV7nImYftJ5MSYZKg9G-tWns1cHrejbt7JAESaszYyCJj84wlLUl0tKIplBDT77x3XuyKncg4bwtQC-o57ZWGpzwQE4BLkM-zZqFMC1RH3Pfr9ppua9CC0GsjXeZr91uFwCXFThZy2DmDudAAK9Cyd74dIVwfhZCuePW1TEOCzgBL003Wtx0cDvgl5cGF3rXL_lxOJOC72bxX1YIacjlXhq0sGiJn7cFVDEf5MZuNSTir6IP1Xk15rLxfNpIw76-AHZrxgWIe99918YIqQzsBwAHyZ9V2eN9V6Mp2WB17JqQqKTjsO_3Co5v2hIioqn9cY\",\n" +
                "    \"siteKey\": \"6LdPxF4dAAAAAIIjs4QB_zOjX2dalZw_ghdUH3Xw\",\n" +
                "    \"userAgent\": \"\",\n" +
                "    \"userIpAddress\": \"\",\n" +
                "    \"expectedAction\": \"signup\"\n" +
                "  },\n" +
                "  \"score\": 0.9,\n" +
                "  \"tokenProperties\": {\n" +
                "    \"valid\": true,\n" +
                "    \"invalidReason\": \"INVALID_REASON_UNSPECIFIED\",\n" +
                "    \"hostname\": \"localhost\",\n" +
                "    \"action\": \"submit\",\n" +
                "    \"createTime\": \"2021-12-03T10:40:21.017Z\"\n" +
                "  },\n" +
                "  \"reasons\": []\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        Assesment assesment = mapper.readValue(responseBody, Assesment.class);
        assertEquals("projects/486622645797/assessments/9893b44998000000", assesment.getName());
        assertEquals(new Event("03AGdBq24pMoA5rctM8V1x757Dcm03PkU47UJ2ryvFYJtYXYQM2hu-COvAdFufMgDJ251lV3HWS4yNtBPJVAU2Q4er5uvAWVpEVYJETXsmobAj-Qg34mHtbBvp90nNLoUtk-ZbefR9-Ft3VdJMF9tvULmHYTTP5jf0gIb0vU6jO1ocrz-pY8_AFN-xCWvWnZngHB-tllLbTfeFnlazP9kI3BSyMaEaVv4IVqIjQIwGr8tnRz90OYnQ4Gl3lXlEdxj-4OOMVyXouy2ZxYcWyS5KgK4niXA5-xVHj2XchYGDILQ4VoiWgeyePK-0hl5TwOB7HbifN9wU6-6w1yTo80VcpYCeOd6Zhyv09XwuFvlKjZmJoS4P5pjMWHdwQbb_93HWXP8sgMdBJIAHiJscm6vn5OnfJuoGfG_7FxSKbeWFx1BPwU4raxmSnYiXe0wBPtGUsK0A7DyjDhTektkTylaoLiebYhTPxWCAGJAQ2LMhMGjJdOodXxxMNzap1MslV7mIOJK5ki_FD0AyW1F3tfusQkyHJ3YK9LLxYk1aeZEgLT8qjRBLXpvvJPcmxIF5n5Wi5En3fD1AtqX3mj2Gsm-H2K2bJYPCo-VRAmBfxUFUC694HVNdhfCAeLemJHrGaJ9LuWD0PJ7acZLFrzPEJB9tJCOltMVra5Kn4FaxfDNixVEmAw5ahvppT5mKcApKHP_HVjcrWYRq8cVohphYPRebh3mTDiCWIF-RQFe9jRA3lRkbN60PSuqzdaFbfY8358LJTpiXVoyMXw380MsaHRofcCczAOV8GBjAadX_4nKRbv4QmcjMDy53GVmrALfHLN7OkRc7sJmBB29iCFmHVva_YYd9ucrUNK4zO2qxCXCyWPUvd2yGy-jCMqV7nImYftJ5MSYZKg9G-tWns1cHrejbt7JAESaszYyCJj84wlLUl0tKIplBDT77x3XuyKncg4bwtQC-o57ZWGpzwQE4BLkM-zZqFMC1RH3Pfr9ppua9CC0GsjXeZr91uFwCXFThZy2DmDudAAK9Cyd74dIVwfhZCuePW1TEOCzgBL003Wtx0cDvgl5cGF3rXL_lxOJOC72bxX1YIacjlXhq0sGiJn7cFVDEf5MZuNSTir6IP1Xk15rLxfNpIw76-AHZrxgWIe99918YIqQzsBwAHyZ9V2eN9V6Mp2WB17JqQqKTjsO_3Co5v2hIioqn9cY", "6LdPxF4dAAAAAIIjs4QB_zOjX2dalZw_ghdUH3Xw", "signup"), assesment.getEvent());
        assertEquals(0.9f, assesment.getScore());
    }
}