package fr.baralecorp.elevia.service.data;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * One top 10 score of the day
 */
public class DayScore implements Comparable<DayScore>{
    private String name;
    private Integer timeMillis;
    private LocalTime hour;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(Integer timeMillis) {
        this.timeMillis = timeMillis;
    }

    public Duration getDuration() {
        return timeMillis == null ? Duration.ZERO : Duration.of(timeMillis, ChronoUnit.MILLIS);
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    @Override
    public int compareTo(DayScore o) {
        if(o == null){
            return 1;
        }
        int ret = 1;
        Duration substract = this.getDuration().minus(o.getDuration());
        if(substract.isNegative()){
            ret = -1;
        } else if(substract.isZero()){
            ret = 0;
        }
        return ret;
    }
}
