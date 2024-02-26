package test;

import data.RegistrationDtoUserGenerator;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


class AuthTest {

    @Test
    void userRegisteredTest() {
        RegistrationDtoUserGenerator.RegistrationDtoUser user = RegistrationDtoUserGenerator.generateValidActive();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("h2").shouldHave(text("Личный кабинет"));
    }

    @Test
    void incorrectUsernameTest() {
        RegistrationDtoUserGenerator.RegistrationDtoUser user = RegistrationDtoUserGenerator.generateInvalidLogin();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка!"))
                .shouldHave(text("Неверно указан логин или пароль"));

    }

    @Test
    void incorrectPasswordTest() {
        RegistrationDtoUserGenerator.RegistrationDtoUser user = RegistrationDtoUserGenerator.generateInvalidPassword();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка!"))
                .shouldHave(text("Неверно указан логин или пароль"));

    }

    @Test
    void noPasswordTest() {
        RegistrationDtoUserGenerator.RegistrationDtoUser user = RegistrationDtoUserGenerator.generateValidActive();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=password]").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void blockedUserSignInTest() {
        RegistrationDtoUserGenerator.RegistrationDtoUser user = RegistrationDtoUserGenerator.generateValidBlocked();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Пользователь заблокирован"));

    }

}




