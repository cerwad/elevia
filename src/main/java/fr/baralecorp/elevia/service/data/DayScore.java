package fr.baralecorp.elevia.service.data;

import java.time.Duration;

/**
 * One top 10 score of the day
 */
public class DayScore implements Comparable<DayScore>{
    private String name;
    private String time;
    private Duration duration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
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
