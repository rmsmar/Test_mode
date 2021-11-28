package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Registration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.generator.UserGenerator.*;

public class AuthTest {
    @BeforeEach
    void Setup() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldRegistatedActive() {
        Registration validValidActiveUser = getValidActiveUser();
        $("[name=login]").setValue(validValidActiveUser.getLogin());
        $("[name=password]").setValue(validValidActiveUser.getPassword());
        $(".button__text").click();
        $(".App_appContainer__3jRx1 h2.heading").waitUntil(exist, 5000);
        $(".App_appContainer__3jRx1 h2.heading").shouldHave(matchesText("Личный кабинет"));
    }

    @Test
    void shouldNotSubmitBlockedUser() {
        Registration validValidAcBlockedUser = getValidBlockedUser();
        $("[name=login]").setValue(validValidAcBlockedUser.getLogin());
        $("[name=password]").setValue(validValidAcBlockedUser.getPassword());
        $(".button__text").click();
        $(".notification_status_error").waitUntil(visible, 5000);
        $(".notification_visible[data-test-id=error-notification]").shouldHave(Condition.matchesText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldNotSubmitWithIncorrectPassword() {
        Registration userWithIncorrectPassword = getUserWithIncorrectPassword();
        $("[name=login]").setValue(userWithIncorrectPassword.getLogin());
        $("[name=password]").setValue(userWithIncorrectPassword.getPassword());
        $(".button__text").click();
        $(".notification_status_error").waitUntil(visible, 5000);
        $(".notification_visible[data-test-id=error-notification]").shouldHave(Condition.matchesText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotSubmitWithIncorrectLogin() {
        Registration userWithIncorrectLogin = getUserWithIncorrectLogin();
        $("[name=login]").setValue(userWithIncorrectLogin.getLogin());
        $("[name=password]").setValue(userWithIncorrectLogin.getPassword());
        $(".button__text").click();
        $(".notification_status_error").waitUntil(visible, 5000);
        $(".notification_visible[data-test-id=error-notification]").shouldHave(Condition.matchesText("Ошибка! Неверно указан логин или пароль"));
    }
}
