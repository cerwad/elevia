package fr.baralecorp.elevia.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public class Result {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int idResult;
    @ManyToOne
    @JoinColumn(name="id", nullable=false)
    private User user;
    private LocalDateTime day;
    private LocalTime time;
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

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
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
                ", user=" + user +
                ", day=" + day +
                ", time=" + time +
                ", nbErrors=" + nbErrors +
                ", exercise=" + exercise +
                '}';
    }
}
