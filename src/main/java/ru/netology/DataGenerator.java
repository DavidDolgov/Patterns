package ru.netology;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

@UtilityClass
public class DataGenerator {

    @UtilityClass
    public static class Registration {

        public String generateDate(long addDays, String pattern) {
            return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
        }

        Random random = new Random();
        int addDays = random.nextInt(10) + 3;
        int addDays1 = random.nextInt(10) + 5;
        String date = generateDate(addDays, "dd");
        String date1 = generateDate(addDays, "dd.MM.yyy");
        String date2 = generateDate(addDays1,"dd.MM.yyyy");

        public static RegistrationInfo generateInfo(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new RegistrationInfo(faker.address().city(),
                    date1, date2,
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.phoneNumber().phoneNumber());
        }
    }
}
