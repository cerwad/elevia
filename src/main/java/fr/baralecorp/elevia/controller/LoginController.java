package fr.baralecorp.elevia.controller;

import fr.baralecorp.elevia.config.app.AppData;
import fr.baralecorp.elevia.config.security.controller.session.IAuthenticationFacade;
import fr.baralecorp.elevia.config.security.gcaptcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController extends BasicController {

    @Autowired
    private AppData appData;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("gre-siteKey", appData.getCaptchaConfig().getSiteKey());
        String view = "login";
        model.addAttribute("view", view);
        return view;
    }
}
