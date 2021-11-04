package fr.baralecorp.elevia.controller;

import fr.baralecorp.elevia.controller.session.IAuthenticationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public class BasicController {

    Logger logger = LoggerFactory.getLogger(BasicController.class);

    protected void addPlayerInfoToModel(Model model, IAuthenticationFacade authenticationFacade){
        Authentication authentication = authenticationFacade.getAuthentication();
        if(authentication != null && authenticationFacade.isUserAuthenticated()){
            model.addAttribute("player", authentication.getPrincipal());
            model.addAttribute("email", authentication.getName());
            logger.info("User connected : "+authentication.getPrincipal()+" "+authentication.getName());
        }
    }

}
