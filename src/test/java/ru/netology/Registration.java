package ru.netology;

import com.github.javafaker.Faker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Locale;

@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class Registration {
    private String login;
    private String password;
    private String status;

    public static Registration generate (String status) {
        Faker faker = new Faker(new Locale("ru"));
        return new Registration(
                faker.name().firstName(),
                faker.internet().password(),
                status
        );
    }
}
