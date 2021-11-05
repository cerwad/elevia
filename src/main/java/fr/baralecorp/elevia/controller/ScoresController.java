package fr.baralecorp.elevia.controller;

import fr.baralecorp.elevia.controller.session.IAuthenticationFacade;
import fr.baralecorp.elevia.controller.transferObj.DayScoreDisplay;
import fr.baralecorp.elevia.service.BestScoresOfDayService;
import fr.baralecorp.elevia.service.data.DayScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ScoresController extends BasicController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private BestScoresOfDayService bestScoresOfDayService;

    @GetMapping("/scores/best")
    public String scores(Model model) {
        addPlayerInfoToModel(model, authenticationFacade);
        String view = "/scores/best";
        model.addAttribute("view", view);
        return view;
    }

    @GetMapping("/scores/topTenDay")
    public String topTenDay(Model model) {
        addPlayerInfoToModel(model, authenticationFacade);

        List<DayScore> topTen = bestScoresOfDayService.getTop10List();
        List<DayScoreDisplay> scores = new ArrayList<>();
        topTen.forEach(d -> scores.add(new DayScoreDisplay(d)));
        model.addAttribute("scores", scores);

        String view = "/scores/topTenDay";
        model.addAttribute("view", view);
        return view;
    }
}
