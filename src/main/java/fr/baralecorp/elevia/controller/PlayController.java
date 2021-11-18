package fr.baralecorp.elevia.controller;

import fr.baralecorp.elevia.controller.session.IAuthenticationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlayController extends BasicController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    Logger logger = LoggerFactory.getLogger(PlayController.class);
    // In the future add the user so we can adapt the exercise
    @GetMapping("/partie")
    public String play(Model model) {
        logger.info("Let's play !");
        addPlayerInfoToModel(model, authenticationFacade);
        String view = "partie";
        model.addAttribute("view", view);
        return view;
    }

}
