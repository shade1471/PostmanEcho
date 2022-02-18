package ru.netology.rest;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Student;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class PostmanEchoTest {

    private Student one = new Student(1, "Andrey Gribanov", "QA", 34);
    private Student two = new Student(2, "Petr Petrov", "SQL", 17);
    private Student three = new Student(3, "Ivan Ivanov", "Python", 24);
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
    void shouldFindStudentWhoseAgeMore18() {
        given()
                .spec(requestSpec)
                .body(students)
                .when()
                .post("/post")
                // Проверки
                .then()
                .body("data.findAll{it.age > 18}.name", hasSize(2))
                .body("data.findAll{it.age > 18}.name[0]", equalTo("Andrey Gribanov"))
                .body("data.findAll{it.age > 18}.name[1]", equalTo("Ivan Ivanov"))
        ;
    }

    @Test
    void shouldFindStudentNotEqual() {
        given()
                .spec(requestSpec)
                .body(students)
                .when()
                .post("/post")
                // Проверки
                .then()
                .body("data.findAll{it.category != 'QA'}.name", hasSize(2))
                .body("data.findAll{it.category != 'QA'}.name[0]", equalTo("Petr Petrov"))
                .body("data.findAll{it.category != 'QA'}.name[1]", equalTo("Ivan Ivanov"))
        ;
    }
}
