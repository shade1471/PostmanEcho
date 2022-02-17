package ru.netology.rest;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Student;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class PostmanEchoTest {

    private Student one = new Student(1, "Andrey Gribanov", "QA", 34);
    private Student two = new Student(2, "Petr Petrov", "SQL", 30);
    private Student three = new Student(3, "Ivan Ivanov", "Python", 17);
    private Student[] mass = {one, two, three};

    private Gson gson = new Gson();
    private String students = gson.toJson(mass);

    private RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("https://postman-echo.com")
            .setContentType("application/json; charset=UTF-8")
            .log(LogDetail.ALL)
            .build();

    @Test
    void shouldCheckCodeResponse() {
        // Given - When - Then
        // Предусловия
        given()
                .spec(requestSpec)
                .body(students)

                .when()
                .post("/post")
                // Проверки
                .then()
                .statusCode(200)
        ;
    }

    @Test
    void shouldCheckSizeResponse() {
        // Given - When - Then
        // Предусловия
        given()
                .spec(requestSpec)
                .body(students)

                .when()
                .post("/post")
                // Проверки
                .then()
                .body("data", hasSize(3))
        ;
    }

    @Test
    void shouldCheckValueResponse() {
        // Given - When - Then
        // Предусловия
        given()
                .spec(requestSpec)
                .body(students)

                .when()
                .post("/post")
                // Проверки
                .then()
                .body("data[0].name", equalTo("Andrey Gribanov"))
        ;
    }

    @Test
    void shouldCheckThatAgeMore18() {
        // Given - When - Then
        // Предусловия
        given()
                .spec(requestSpec)
                .body(students)

                .when()
                .post("/post")
                // Проверки
                .then()
                .body("data.every{it.age > 18}", is(false))
        ;
    }

    @Test
    void shouldCheckThatAgeMore181111() {
        // Given - When - Then
        // Предусловия
        given()
                .spec(requestSpec)
                .body(students)

                .when()
                .post("/post")
                // Проверки
                .then()
                .body("data.every{it.age > 18}.name", equalTo("Andrey Gribanov"))
        ;
    }
}
