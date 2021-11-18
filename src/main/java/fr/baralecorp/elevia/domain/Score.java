package fr.baralecorp.elevia.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Entity
public class Score implements Comparable<Score> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "idResult", nullable = false)
    private Result result;
    private Duration time;
    private ExerciseType exercise;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Duration getTime() {
        return time;
    }

    public void setTime(Duration time) {
        this.time = time;
    }

    public ExerciseType getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseType exercise) {
        this.exercise = exercise;
    }

    /**
     * Builds a Score from a Result
     *
     * @param result the result
     * @return new Score
     */
    public static Score of(Result result) {
        Score s = new Score();
        s.setExercise(result.getExercise());
        s.setResult(result);
        s.setTime(result.getTime());
        return s;
    }


    /**
     * Enable to sort the scores by their time and exercise type
     *
     * @param s
     * @return the result of the comparaison
     */
    @Override
    public int compareTo(@NotNull Score s) {
        int comp = -1;
        if (s != null) {
            comp = this.getExercise().compareTo(s.getExercise());
            if (comp == 0) {
                comp = this.getTime().compareTo(s.getTime());
            }
        }
        return comp;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", day=" + result.getDay() +
                ", user=" + result.getUser().getHandle() +
                ", time=" + time +
                ", exercise=" + exercise +
                '}';
    }
}
