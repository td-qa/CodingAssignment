package codingassignment.tests.base;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {
    protected static RequestSpecification requestSpec;

    @BeforeAll
    static void apiSetup() {
        RestAssured.baseURI = "https://api.restful-api.dev";

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        requestSpec = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
}
