package fr.baralecorp.elevia.controller;

import fr.baralecorp.elevia.controller.session.IAuthenticationFacade;
import fr.baralecorp.elevia.service.BestScoresOfDayService;
import fr.baralecorp.elevia.service.data.DayScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ScoresController extends BasicController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private BestScoresOfDayService bestScoresOfDayService;

    @GetMapping("/scores")
    public String scores(Model model) {
        addPlayerInfoToModel(model, authenticationFacade);
        String view = "scores";
        model.addAttribute("view", view);
        return view;
    }

    @GetMapping("/scores/topTenDay")
    public String topTenDay(Model model) {
        addPlayerInfoToModel(model, authenticationFacade);

        List<DayScore> scores = bestScoresOfDayService.getTop10List();
        model.addAttribute("topTen", scores);

        String view = "topTenDay";
        model.addAttribute("view", view);
        return view;
    }
}
