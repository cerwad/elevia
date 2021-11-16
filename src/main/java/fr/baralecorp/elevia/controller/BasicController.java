package fr.baralecorp.elevia.controller;

import fr.baralecorp.elevia.controller.session.IAuthenticationFacade;
import fr.baralecorp.elevia.controller.transferObj.UserDisplay;
import fr.baralecorp.elevia.domain.User;
import fr.baralecorp.elevia.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public class BasicController {

    Logger logger = LoggerFactory.getLogger(BasicController.class);

    protected void addPlayerInfoToModel(Model model, IAuthenticationFacade authenticationFacade) {
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

}
