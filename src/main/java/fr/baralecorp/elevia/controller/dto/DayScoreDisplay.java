package fr.baralecorp.elevia.controller.dto;

import fr.baralecorp.elevia.service.data.DayScore;

import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;

public class DayScoreDisplay extends BasicScoreDisplay {

    public DayScoreDisplay() {

    }

    public DayScoreDisplay(@NotNull DayScore dayScore) {
        this.handle = dayScore.getName();
        this.time = genTimeDisplay(dayScore.getDuration());
        this.hour = dayScore.getHour().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    private String hour;


    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "DayScoreDisplay{" +
                "handle='" + handle + '\'' +
                ", time='" + time + '\'' +
                ", hour='" + hour + '\'' +
                '}';
    }
}
