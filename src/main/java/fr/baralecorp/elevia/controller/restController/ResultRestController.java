package fr.baralecorp.elevia.controller.restController;

import fr.baralecorp.elevia.controller.BasicController;
import fr.baralecorp.elevia.controller.session.IAuthenticationFacade;
import fr.baralecorp.elevia.controller.transferObj.ExerciseResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultRestController extends BasicController {

    Logger logger = LoggerFactory.getLogger(ResultRestController.class);

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    // When Spring Sec add the User or Session
    @PostMapping("/success")
    void success(ExerciseResults exerciseResults, Model model) {
        addPlayerInfoToModel(model, authenticationFacade);
        logger.info(String.format("Exercise success with %d errors in %d seconds and %d tenths",
                exerciseResults.getNbErrors(), exerciseResults.getSeconds(), exerciseResults.getSecondTenths()));

        if(authenticationFacade.isUserAuthenticated()){
            // add score in the DB
            logger.info("Adding score in the DB");

        }

    }
}
