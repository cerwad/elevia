package fr.baralecorp.elevia.controller.restController;

import fr.baralecorp.elevia.service.BestScoresOfDayService;
import fr.baralecorp.elevia.service.data.DayScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
public class TopTenController {

    Logger logger = LoggerFactory.getLogger(TopTenController.class);

    @Autowired
    private BestScoresOfDayService bestScoresOfDayService;

    @RequestMapping(value = "/topTen/isin", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isInTopTen(int time){
        logger.info("Call to is in Top ten");
        return bestScoresOfDayService.isTop10(Duration.ofMillis(time));
    }

    @RequestMapping(value = "/topTen/add", method = RequestMethod.POST)
    public void addNewTopTen(DayScore dayScore){
        bestScoresOfDayService.addNewScore(dayScore);
    }

}
