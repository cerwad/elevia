package fr.baralecorp.elevia.service.util;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static DateTimeFormatter DATE_FRENCH_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    @NotNull
    public static int calcAge(LocalDate birth) {
        return (int) ChronoUnit.YEARS.between(birth, LocalDate.now());
    }
}
