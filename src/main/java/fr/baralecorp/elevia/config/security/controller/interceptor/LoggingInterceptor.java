package fr.baralecorp.elevia.config.security.controller.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    /**
     * Logging interceptor to log requests
     *
     * @param req     request
     * @param reqBody nody
     * @param ex      requestExec
     * @return response
     * @throws IOException if an io problem happens
     */
    @Override
    public @NonNull
    ClientHttpResponse intercept(
            HttpRequest req, @NonNull byte[] reqBody, ClientHttpRequestExecution ex) throws IOException {
        LOGGER.debug("Request headers: {}", req.getHeaders());
        LOGGER.debug("Request body: {}", new String(reqBody, StandardCharsets.UTF_8));
        ClientHttpResponse response = ex.execute(req, reqBody);
        InputStreamReader isr = new InputStreamReader(
                response.getBody(), StandardCharsets.UTF_8);
        String body = new BufferedReader(isr).lines()
                .collect(Collectors.joining("\n"));
        LOGGER.debug("Response body: {}", body);
        return response;
    }
}