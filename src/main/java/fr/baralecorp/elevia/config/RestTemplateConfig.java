package fr.baralecorp.elevia.config;

import fr.baralecorp.elevia.service.LoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    private final static Logger logger = LoggerFactory.getLogger(RestTemplateConfig.class);

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        RestTemplate restTemplate = builder
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
        if (logger.isDebugEnabled()) {
            ClientHttpRequestFactory factory =
                    new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
            restTemplate.setRequestFactory(factory);
            List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
            if (CollectionUtils.isEmpty(interceptors)) {
                interceptors = new ArrayList<>();
            }
            interceptors.add(new LoggingInterceptor());
            restTemplate.setInterceptors(interceptors);
        } else {
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        }
        return restTemplate;
    }
}
