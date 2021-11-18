package fr.baralecorp.elevia.controller;

import fr.baralecorp.elevia.controller.session.IAuthenticationFacade;
import fr.baralecorp.elevia.controller.transferObj.DayScoreDisplay;
import fr.baralecorp.elevia.controller.transferObj.ScoreDisplay;
import fr.baralecorp.elevia.domain.ExerciseType;
import fr.baralecorp.elevia.domain.Score;
import fr.baralecorp.elevia.service.BestScoresOfDayService;
import fr.baralecorp.elevia.service.BestScoresService;
import fr.baralecorp.elevia.service.data.DayScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("scores")
public class ScoresController extends BasicController {

    Logger logger = LoggerFactory.getLogger(ScoresController.class);

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private BestScoresOfDayService bestScoresOfDayService;

    @Autowired
    private BestScoresService bestScoresService;

    @GetMapping("/best")
    public String scores(Model model) {
        addPlayerInfoToModel(model, authenticationFacade);
        List<Score> scores = bestScoresService.getScores(ExerciseType.MULTIPLICATION, 10);
        logger.info("Scores : " + scores);
        List<ScoreDisplay> listScores = new ArrayList<>();
        scores.forEach(s -> listScores.add(new ScoreDisplay(s)));
        logger.info("DisplayScores : " + listScores);
        model.addAttribute("scores", listScores);

        String view = "scores/best";
        model.addAttribute("view", view);
        return view;
    }

    @GetMapping("/topTenDay")
    public String topTenDay(Model model) {
        addPlayerInfoToModel(model, authenticationFacade);

        List<DayScore> topTen = bestScoresOfDayService.getTop10List();
        List<DayScoreDisplay> scores = new ArrayList<>();
        topTen.forEach(d -> scores.add(new DayScoreDisplay(d)));
        model.addAttribute("scores", scores);

        String view = "scores/topTenDay";
        model.addAttribute("view", view);
        return view;
    }
}
