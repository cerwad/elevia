package fr.baralecorp.elevia.dao;

import fr.baralecorp.elevia.domain.ExerciseType;
import fr.baralecorp.elevia.domain.Score;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends CrudRepository<Score, Long> {
    List<Score> findByExercise(ExerciseType exerciseType);
}
