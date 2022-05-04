package fr.baralecorp.elevia.controller.dto;

import fr.baralecorp.elevia.domain.Score;

import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;

public class ScoreDisplay extends BasicScoreDisplay {


    public ScoreDisplay() {

    }

    public ScoreDisplay(@NotNull Score score) {
        this.handle = score.getResult().getUser().getHandle();
        this.time = genTimeDisplay(score.getTime());
        this.date = score.getResult().getDay().format(DateTimeFormatter.ISO_DATE);
        this.age = score.getResult().getUser().getAge();
    }


    private String date;
    private int age;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ScoreDisplay{" +
                "handle='" + handle + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", age=" + age +
                '}';
    }
}
