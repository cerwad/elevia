package fr.baralecorp.elevia.service;

import fr.baralecorp.elevia.service.data.DayScore;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service storing in memory the best scores of the day
 */
@Service
public class BestScoresOfDayService {

    private List<DayScore> top10List;
    private LocalDate currentDay;
    private final static int MAX_ENTRIES = 10;

    public BestScoresOfDayService(){
        currentDay = LocalDate.now();
        top10List = new ArrayList<>();
    }

    public boolean isTop10(Duration time){
        checkIfDayChanged();
        if(top10List.size() < MAX_ENTRIES){
            return true;
        }
        if(time.isZero()){
            return false;
        }
        DayScore last = top10List.get(MAX_ENTRIES-1);
        return !last.getDuration().minus(time).isNegative();
    }

    public void addNewScore(DayScore newScore){
        checkIfDayChanged();
        if(isTop10(newScore.getDuration())) {
            newScore.setHour(LocalTime.now());
            top10List.add(newScore);
            top10List = top10List.stream().sorted().collect(Collectors.toList());
            if (top10List.size() > MAX_ENTRIES) {
                top10List.remove(MAX_ENTRIES);
            }
        }
    }

    public List<DayScore> getTop10List(){
        checkIfDayChanged();
        return top10List;
    }

    protected void checkIfDayChanged(){
        if(!currentDay.equals(LocalDate.now())){
            top10List = new ArrayList<>();
            currentDay = LocalDate.now();
        }
    }
}
