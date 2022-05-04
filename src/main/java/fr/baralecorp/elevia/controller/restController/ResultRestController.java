package fr.baralecorp.elevia.controller.restController;

import fr.baralecorp.elevia.config.security.controller.session.IAuthenticationFacade;
import fr.baralecorp.elevia.controller.BasicController;
import fr.baralecorp.elevia.controller.dto.ExerciseResults;
import fr.baralecorp.elevia.dao.ResultRepository;
import fr.baralecorp.elevia.domain.Result;
import fr.baralecorp.elevia.service.BestScoresService;
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

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private BestScoresService bestScoresService;

    // When Spring Sec add the User or Session
    @PostMapping("/success")
    void success(ExerciseResults exerciseResults, Model model) {
        logger.info(String.format("Exercise success with %d errors in %d seconds and %d tenths",
                exerciseResults.getNbErrors(), exerciseResults.getSeconds(), exerciseResults.getSecondTenths()));

        if (authenticationFacade.isUserAuthenticated()) {
            // add score in the DB
            logger.info("Adding score in the DB");
            Result newResult = Result.of(exerciseResults, authenticationFacade.getAuthenticatedUser());

            newResult = resultRepository.save(newResult);
            bestScoresService.addNewScore(newResult);

        }

    }
}
