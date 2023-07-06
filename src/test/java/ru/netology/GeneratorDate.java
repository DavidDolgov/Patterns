package ru.netology;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@UtilityClass
public class GeneratorDate {

    Random random = new Random();
    int addDays = 3;
    int planningAmount = 20;
    int randomDays = random.nextInt(planningAmount) + addDays;

    public String generateDate(String pattern) {
        return LocalDate.now().plusDays(randomDays).format(DateTimeFormatter.ofPattern(pattern));
    }
}

