package fr.baralecorp.elevia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScoresController {

    @GetMapping("/scores")
    public String scores(Model model) {
        String view = "scores";
        model.addAttribute("view", view);
        return view;
    }
}
