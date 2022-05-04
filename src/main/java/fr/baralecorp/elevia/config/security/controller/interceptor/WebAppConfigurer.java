package fr.baralecorp.elevia.config.security.controller.interceptor;

import fr.baralecorp.elevia.config.security.controller.resolver.RecaptchaTokenArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Component
public class WebAppConfigurer implements WebMvcConfigurer {
    @Autowired
    private ModelInterceptor modelInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(modelInterceptor);
    }

    @Override
    public void addArgumentResolvers(
            List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RecaptchaTokenArgumentResolver());
    }
}