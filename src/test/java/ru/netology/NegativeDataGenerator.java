package ru.netology;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

@UtilityClass
public class NegativeDataGenerator {

    public String noCity(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().firstName();
    }

    public String noDate(String pattern) {
        Random random = new Random();
        int addDays = 0;
        int planningAmount = 2;
        int randomDays = random.nextInt(planningAmount) + addDays;
        return LocalDate.now().plusDays(randomDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    public String randomDate() {
        Random random = new Random();
        int day = random.nextInt(99) + 30;
        int month = random.nextInt(99) + 12;
        int year = random.nextInt(10000) + 2500;
        return day + "." + month + "." + year;
    }

    public String nameAnotherLocation(String locale) {
        String locale1 = "en";
        if (locale1.equals(locale)) {
            locale1 = "ru";
        }
        Faker faker = new Faker(new Locale(locale1));
        return faker.name().fullName();
    }

    public String noNumber() {
        Random random = new Random();
        return "+" + random.nextInt(10000);
    }

    public String randomWord(String locale){
        Faker faker = new Faker(new Locale(locale));
        return faker.animal().name();
    }

}
