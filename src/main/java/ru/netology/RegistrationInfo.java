package ru.netology;

import com.github.javafaker.DateAndTime;
import lombok.Data;

import java.util.Date;

@Data
public class RegistrationInfo {

    private final String city;
    private final String  date;
    private final String date2;
    private final String firstName;
    private final String lastName;
    private final String phone;

}
