package fr.baralecorp.elevia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String scores(Model model) {
        String view = "login";
        model.addAttribute("view", view);
        return view;
    }
}
