package org.example;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class RegistrationDtoUserGenerator {
    private final static String activeStatus = "active";
    private final static String blockedStatus = "blocked";

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static final Gson gson = new Gson();
    private static final Faker faker = new Faker(new Locale("en"));

    private RegistrationDtoUserGenerator() {
    }

    private static void registrationUsers(RegistrationDtoUser userData) {
        given()
                .spec(requestSpec)
                .body(gson.toJson(userData))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static RegistrationDtoUser generateValidActive() {
        RegistrationDtoUser userData = new RegistrationDtoUser(faker.name().username(),
                faker.internet().password(), activeStatus);
        registrationUsers(userData);
        return userData;
    }

    public static RegistrationDtoUser generateValidBlocked() {
        RegistrationDtoUser userData = new RegistrationDtoUser(faker.name().username(),
                faker.internet().password(), blockedStatus);
        registrationUsers(userData);
        return userData;
    }

    public static RegistrationDtoUser generateInvalidLogin() {
        String password = faker.internet().password();
        RegistrationDtoUser userData = new RegistrationDtoUser(faker.name().username(),
                password, activeStatus);
        registrationUsers(userData);
        return new RegistrationDtoUser(faker.name().username(),
                password, activeStatus);
    }

    public static RegistrationDtoUser generateInvalidPassword() {
        String login = faker.name().username();
        RegistrationDtoUser userData = new RegistrationDtoUser(login,
                faker.internet().password(), activeStatus);
        registrationUsers(userData);
        return new RegistrationDtoUser(login,
                faker.internet().password(), activeStatus);
    }
}
