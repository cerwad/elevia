package fr.baralecorp.elevia.config.security.controller.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class RecaptchaTokenArgumentResolver
        implements HandlerMethodArgumentResolver {

    Logger logger = LoggerFactory.getLogger(RecaptchaTokenArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(RecaptchaToken.class) != null;
    }

    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory) throws Exception {
        logger.debug("Resolving Recaptcha Token");
        HttpServletRequest request
                = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        logger.debug("Parameter values: " + request.getParameterMap().entrySet().toString());
        return (String) request.getParameter("g-recaptcha-response");
    }
}