package fr.baralecorp.elevia.service;

import com.google.common.collect.Lists;
import fr.baralecorp.elevia.dao.ResultRepository;
import fr.baralecorp.elevia.dao.ScoreRepository;
import fr.baralecorp.elevia.domain.ExerciseType;
import fr.baralecorp.elevia.domain.Result;
import fr.baralecorp.elevia.domain.Score;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service managing the list of best scores including being able to regenerate the scores from the results
 */
@Service
public class BestScoresService {

    private static final int MAX_BEST_SCORES = 20;
    Logger logger = LoggerFactory.getLogger(SecurityUserDetailsService.class);

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ResultRepository resultRepository;

    public void generateScoresFromResults() {
        if (scoreRepository.count() > 0) {
            scoreRepository.deleteAll();
        }
        List<Result> results = Lists.newArrayList(resultRepository.findAll());
        results = results.stream().sorted().collect(Collectors.toList());
        logger.info("Results: " + results.toString());
        ExerciseType type = null;
        int count = 0;
        List<Score> bestScores = new ArrayList<>();
        for (Result result : results) {
            if (type != result.getExercise()) {
                type = result.getExercise();
                count = 0;
            }
            if (count < MAX_BEST_SCORES && result.getNbErrors() == 0) {
                bestScores.add(Score.of(result));
                count++;
            }
        }
        logger.info("Scores: " + bestScores.toString());
        scoreRepository.saveAll(bestScores);
    }

    public List<Score> getScores(ExerciseType type, int rowNumber) {
        List<Score> scores = sort(scoreRepository.findByExercise(type));
        if (scores.size() > rowNumber) {
            scores.subList(0, rowNumber);
        }
        return scores;
    }

    public void addNewScore(Result result) {
        if (result.getNbErrors() == 0) {
            List<Score> scores = sort(scoreRepository.findByExercise(result.getExercise()));
            if (scores.size() < MAX_BEST_SCORES || scores.get(MAX_BEST_SCORES - 1).getTime().getNano() > result.getTime().getNano()) {
                if (scores.size() >= MAX_BEST_SCORES) {
                    scoreRepository.delete(scores.get(MAX_BEST_SCORES - 1));
                }
                scoreRepository.save(Score.of(result));
            }
        }
    }

    private List<Score> sort(List<Score> scores) {
        return scores.stream().sorted().collect(Collectors.toList());
    }
}
