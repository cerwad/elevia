package fr.baralecorp.elevia.controller;

import fr.baralecorp.elevia.controller.transferObj.ExerciseResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultRestController {

    Logger logger = LoggerFactory.getLogger(ResultRestController.class);

    // When Spring Sec add the User or Session
    @PostMapping("/success")
    void success(ExerciseResults exerciseResults, Model model) {
        logger.info(String.format("Exercise success with %d errors in %d seconds",
                exerciseResults.getNbErrors(), exerciseResults.getSeconds()));
        // add score in the DB
    }
}
