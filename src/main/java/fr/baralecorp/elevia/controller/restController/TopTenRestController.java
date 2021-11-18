package fr.baralecorp.elevia.controller.restController;

import fr.baralecorp.elevia.service.BestScoresOfDayService;
import fr.baralecorp.elevia.service.data.DayScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
public class TopTenRestController {

    Logger logger = LoggerFactory.getLogger(TopTenRestController.class);

    @Autowired
    private BestScoresOfDayService bestScoresOfDayService;

    @RequestMapping(value = "/topTen/isin", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isInTopTen(int time){
        logger.info("Call to is in Top ten with time "+time);
        return bestScoresOfDayService.isTop10(Duration.ofMillis(time));
    }

    @RequestMapping(value = "/topTen/add", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addNewTopTen(@RequestBody DayScore dayScore) throws Exception {
        if(dayScore == null){
            throw new Exception("Cannot add a blank score");
        }
        logger.info(String.format("Adds a new Top ten score: %s %s", dayScore.getName(), dayScore.getTimeMillis()));
        bestScoresOfDayService.addNewScore(dayScore);
    }

}
