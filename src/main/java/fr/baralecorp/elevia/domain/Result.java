package fr.baralecorp.elevia.domain;

import fr.baralecorp.elevia.controller.transferObj.ExerciseResults;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Result implements Comparable<Result> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idResult;
    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;
    private LocalDateTime day;
    private Duration time;
    private short nbErrors;
    private ExerciseType exercise;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDay() {
        return day;
    }

    public void setDay(LocalDateTime day) {
        this.day = day;
    }

    public Duration getTime() {
        return time;
    }

    public void setTime(Duration time) {
        this.time = time;
    }

    public short getNbErrors() {
        return nbErrors;
    }

    public void setNbErrors(short nbErrors) {
        this.nbErrors = nbErrors;
    }

    public ExerciseType getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseType exercise) {
        this.exercise = exercise;
    }

    public int getIdResult() {
        return idResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return idResult == result.idResult;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idResult);
    }

    @Override
    public String toString() {
        return "Result{" +
                "idResult=" + idResult +
                ", user=" + user.getHandle() +
                ", day=" + day +
                ", time=" + time +
                ", nbErrors=" + nbErrors +
                ", exercise=" + exercise +
                '}';
    }

    @Override
    public int compareTo(Result r) {
        int comp = -1;
        if (r != null) {
            comp = this.getExercise().compareTo(r.getExercise());
            if (comp == 0) {
                comp = this.getTime().compareTo(r.getTime());
            }
        }
        return comp;
    }

    public static Result of(ExerciseResults exerciseResults, User user) {
        Result ret = new Result();
        ret.setDay(LocalDateTime.now());
        ret.setExercise(exerciseResults.getExerciseType());
        ret.setNbErrors((short) 0);
        if (exerciseResults.getNbErrors() != null) {
            ret.setNbErrors(exerciseResults.getNbErrors().shortValue());
        }
        ret.setTime(exerciseResults.getTime());
        ret.setUser(user);

        return ret;
    }
}
