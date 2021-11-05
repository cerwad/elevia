package fr.baralecorp.elevia.controller.transferObj;

import fr.baralecorp.elevia.service.data.DayScore;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class DayScoreDisplay {

    public DayScoreDisplay(){

    }

    public DayScoreDisplay(@NotNull DayScore dayScore){
        this.name = dayScore.getName();
        this.seconds = genTimeDisplay(dayScore.getDuration());
        this.hour = dayScore.getHour().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    private String name;
    private String seconds;
    private String hour;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    private String genTimeDisplay(Duration dur){
        String time = "";
        if(dur.getNano() == 0){
            time = String.format("%d s", dur.getSeconds());
        } else {
            time = String.format("%ds %d centièmes", dur.getSeconds(), dur.getNano()/10000000);
        }
        return time;
    }
}
