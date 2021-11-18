package fr.baralecorp.elevia.controller;

import fr.baralecorp.elevia.controller.session.IAuthenticationFacade;
import fr.baralecorp.elevia.controller.transferObj.UserDisplay;
import fr.baralecorp.elevia.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController extends BasicController {

    Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private UserService userService;

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@ModelAttribute("user") UserDisplay player, @PathVariable("id") long id, Model model) {
        if (authenticationFacade.isUserAuthenticated()) {
            if (id != authenticationFacade.getAuthenticatedUser().getId()) {
                throw new SecurityException("User allowed to modify only his info");
            }
        } else {
            throw new RuntimeException("Warning Unauthorized user accessed protected service");
        }
        player = userService.getUserById(id);
        model.addAttribute("user", player);
        addPlayerInfoToModel(model, authenticationFacade);
        return "user/update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid @ModelAttribute("user") UserDisplay user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "user/update-user";
        }

        userService.save(user);
        addPlayerInfoToModel(model, authenticationFacade);
        return "redirect:user/update-user";
    }

}