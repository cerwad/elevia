package fr.baralecorp.elevia.controller.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class ModelInterceptorAppConfig implements WebMvcConfigurer {
   @Autowired
   private ModelInterceptor modelInterceptor;

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(modelInterceptor);
   }
}