import com.google.gson.Gson;
import groovy.util.logging.Log;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostmanEchoTest {

    @Test
    void shouldReturnPostResponse() {
        // Given - When - Then
        // Предусловия
        Student one =new Student("Andrey Gribanov","QA",34);
        Gson gson =new Gson();

        given()
                .baseUri("https://postman-echo.com")
                .contentType("text/plain; charset=UTF-8")
                .body(gson.toJson(one))
            // отправляемые данные (заголовки и query можно выставлять аналогично)
                // Выполняемые действия
                .when()
                .post("/post")
                // Проверки
                .then()
                .statusCode(200)
                .body("data.age",equalTo("34"))
                ;

    }

}
