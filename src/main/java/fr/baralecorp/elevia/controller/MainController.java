package fr.baralecorp.elevia.controller;

import com.google.common.base.Strings;
import fr.baralecorp.elevia.controller.resolver.RecaptchaToken;
import fr.baralecorp.elevia.controller.session.IAuthenticationFacade;
import fr.baralecorp.elevia.controller.transferObj.UserDisplay;
import fr.baralecorp.elevia.security.CaptchaService;
import fr.baralecorp.elevia.service.UserService;
import fr.baralecorp.elevia.service.data.AppData;
import fr.baralecorp.elevia.service.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MainController extends BasicController {

    Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private AppData appData;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/index", "/"})
    public String index(Model model) {

        String view = "index";
        model.addAttribute("view", view);
        return view;
    }

    @GetMapping("/signup")
    public String showSignUpForm(@ModelAttribute("user") UserDisplay player, Model model) {
        model.addAttribute("gre_siteKey", appData.getCaptchaConfig().getSiteKey());
        String view = "signup";
        model.addAttribute("view", view);
        return view;
    }

    @PostMapping("/adduser")
    public String addUser(@Valid @ModelAttribute("user") UserDisplay player, @RecaptchaToken String recaptchaToken, BindingResult result, Model model) {
        validateUser(player, result);
        if (result.hasErrors()) {
            model.addAttribute("user", player);
            if (result.hasFieldErrors()) {
                logger.info(result.getErrorCount() + " Validation Error on field " + result.getFieldError().getField() + " error: " + result.getFieldError().getDefaultMessage());
            } else if (result.hasGlobalErrors()) {
                logger.info(result.getErrorCount() + " Validation Error " + result.getGlobalError().getDefaultMessage());
            }
            model.addAttribute("gre_siteKey", appData.getCaptchaConfig().getSiteKey());
            return "signup";
        }
        logger.debug("Token: " + recaptchaToken + " Model: " + model.toString());
        if (Strings.isNullOrEmpty(recaptchaToken)) {
            throw new SecurityException("Did not receive captcha token");
        }
        captchaService.verifyUserAction("signup", recaptchaToken);
        logger.info("Creating new user: " + player.toString());
        userService.save(player);
        return "redirect:/index";
    }

    private void validateUser(UserDisplay userDisplay, BindingResult result) {
        if (DateUtil.calcAge(userDisplay.getBirthDate()) <= 5) {
            result.addError(new FieldError(result.getObjectName(), "birthDate", "L'age minimum pour s'inscrire est de 6 ans"));
        }
        Optional<UserDisplay> user = userService.getUserByHandle(userDisplay.getHandle());
        if (user.isPresent()) {
            result.addError(new FieldError(result.getObjectName(), "handle", "Ce pseudo existe déjà"));
        }

        user = userService.getUserByEmail(userDisplay.getEmail());
        if (user.isPresent()) {
            result.addError(new FieldError(result.getObjectName(), "email", "Cet email existe déjà"));
        }
    }
}
