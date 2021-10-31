package fr.baralecorp.elevia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlayController {

    Logger logger = LoggerFactory.getLogger(PlayController.class);
    // In the future add the user so we can adapt the exercise
    @GetMapping("/partie")
    public String play(Model model) {
        logger.info("Let's play !");
        String view = "partie";
        model.addAttribute("view", view);
        return view;
    }

}
