package fr.baralecorp.elevia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/index")
    public String index(Model model) {

        String view = "index";
        model.addAttribute("view", view);
        return view;
    }


}
