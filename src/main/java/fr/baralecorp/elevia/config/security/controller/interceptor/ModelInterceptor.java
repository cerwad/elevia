package fr.baralecorp.elevia.config.security.controller.interceptor;

import fr.baralecorp.elevia.config.app.AppData;
import fr.baralecorp.elevia.config.security.controller.session.IAuthenticationFacade;
import fr.baralecorp.elevia.controller.dto.UserDisplay;
import fr.baralecorp.elevia.domain.User;
import fr.baralecorp.elevia.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ModelInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(ModelInterceptor.class);

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private AppData appData;

    protected void addPlayerInfoToModel(ModelMap model) {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication != null && authenticationFacade.isUserAuthenticated()) {
            UserDisplay userDisplay = UserService.convertUser((User) authentication.getPrincipal());
            model.addAttribute("id", userDisplay.getId());
            model.addAttribute("player", userDisplay);
            model.addAttribute("handle", userDisplay.getHandle());
            model.addAttribute("authenticated", true);
            logger.info("User connected : " + authentication.getPrincipal() + " " + authentication.getName());
        } else {
            model.addAttribute("authenticated", false);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        if (modelAndView != null) {
            logger.debug(request.getRequestURI() + " Request intercepted env: " + appData.getEnv());
            ModelMap modelMap = modelAndView.getModelMap();
            addPlayerInfoToModel(modelMap);
            modelMap.addAttribute("envir", appData.getEnv().toString());
        }
    }
}
