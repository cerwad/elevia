package fr.baralecorp.elevia.controller;

import fr.baralecorp.elevia.controller.session.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController extends BasicController {


    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @GetMapping("/login")
    public String scores(Model model) {
        addPlayerInfoToModel(model, authenticationFacade);
        String view = "login";
        model.addAttribute("view", view);
        return view;
    }
}
