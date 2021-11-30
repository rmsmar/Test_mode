package ru.netology.test;

import com.codeborne.selenide.Condition;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.generator.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.generator.DataGenerator.Registration.getUser;
import static ru.netology.generator.DataGenerator.getRandomLogin;
import static ru.netology.generator.DataGenerator.getRandomPassword;

public class AuthTest {
    @BeforeEach
    public void shouldOpenForm() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $$("button").find(Condition.text("Продолжить")).click();
        $(byText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $$("button").find(Condition.text("Продолжить")).click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    @Test
    public void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $$("button").find(Condition.text("Продолжить")).click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Пользователь заблокирован"))
                .shouldBe(Condition.visible);
    }

    @Test
    public void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $$("button").find(Condition.text("Продолжить")).click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $$("button").find(Condition.text("Продолжить")).click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }
}
