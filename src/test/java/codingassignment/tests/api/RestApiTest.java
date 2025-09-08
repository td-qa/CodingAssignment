package codingassignment.tests.api;

import codingassignment.tests.base.BaseApiTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestApiTest extends BaseApiTest {
    @Test
    void verifyThatIdsAreUniqueAndNamesContainApple() {
        var json = given().spec(requestSpec)
                .when().get("/objects")
                .then().statusCode(200)
                .extract().jsonPath();

        var ids = json.getList("id", String.class);
        var names = json.getList("name", String.class);

        var seen = new java.util.LinkedHashSet<String>();
        var duplicateIds = ids.stream()
                .filter(id -> !seen.add(id))
                .distinct()
                .toList();
        assertTrue(duplicateIds.isEmpty(), "Duplicate ids: " + duplicateIds);

        var nonAppleNames = names.stream()
                .filter(n -> n == null || !n.toLowerCase().contains("apple"))
                .toList();
        assertTrue(nonAppleNames.isEmpty(), "Names not containing 'Apple': " + nonAppleNames);
    }
}
