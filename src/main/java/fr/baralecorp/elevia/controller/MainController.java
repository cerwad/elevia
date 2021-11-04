package fr.baralecorp.elevia.controller;

import fr.baralecorp.elevia.controller.session.IAuthenticationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController extends BasicController {

    Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @RequestMapping(value={"/index", "/"})
    public String index(Model model) {
        addPlayerInfoToModel(model, authenticationFacade);

        String view = "index";
        model.addAttribute("view", view);
        return view;
    }


}
