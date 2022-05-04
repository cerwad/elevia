package fr.baralecorp.elevia.controller.dto;

import fr.baralecorp.elevia.domain.ExerciseType;

import java.time.Duration;

public class ExerciseResults {
    private Integer nbErrors;
    private String exercise;
    private Integer minutes;
    private Integer seconds;
    private Integer secondTenths;

    public ExerciseResults(Integer minutes, Integer seconds, Integer secondTenths, Integer nbErrors, String exercise) {
        this.nbErrors = nbErrors;
        this.exercise = exercise;
        this.minutes = minutes;
        this.seconds = seconds;
        this.secondTenths = secondTenths;
    }

    public Duration getTime() {
        return Duration.ofMillis(minutes * 60000 + seconds * 1000 + secondTenths * 100);
    }

    public Integer getNbErrors() {
        return nbErrors;
    }

    public void setNbErrors(Integer nbErrors) {
        this.nbErrors = nbErrors;
    }

    public ExerciseType getExerciseType() {
        return ExerciseType.valueOf(exercise);
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public Integer getSecondTenths() {
        return secondTenths;
    }

    public void setSecondTenths(Integer secondTenths) {
        this.secondTenths = secondTenths;
    }
}
