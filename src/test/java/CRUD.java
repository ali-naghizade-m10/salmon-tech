import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CRUD {

        private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

        @BeforeAll
        public static void setUp() {
            RestAssured.baseURI = BASE_URL;
        }

        @Test
        public void testCreatePost() {
            given()
                    .contentType(ContentType.JSON)
                    .body("{\"title\": \"Test Title\", \"body\": \"Test Body\", \"userId\": 1}")
                    .when()
                    .post("/posts")
                    .then()
                    .statusCode(201)
                    .body("title", equalTo("Test Title"))
                    .body("body", equalTo("Test Body"))
                    .body("userId", equalTo(1));
        }

        @Test
        public void testGetPost() {
            Response response = given()
                    .pathParam("postId", 1)
                    .when()
                    .get("/posts/{postId}");

            response.then()
                    .statusCode(200)
                    .body("id", equalTo(1))
                    .body("title", notNullValue())
                    .body("body", notNullValue())
                    .body("userId", notNullValue());
        }

        @Test
        public void testUpdatePost() {
            given()
                    .contentType(ContentType.JSON)
                    .body("{\"title\": \"Updated Title\", \"body\": \"Updated Body\", \"userId\": 1}")
                    .pathParam("postId", 1)
                    .when()
                    .put("/posts/{postId}")
                    .then()
                    .statusCode(200)
                    .body("title", equalTo("Updated Title"))
                    .body("body", equalTo("Updated Body"))
                    .body("userId", equalTo(1));
        }

        @Test
        public void testDeletePost() {
            given()
                    .pathParam("postId", 1)
                    .when()
                    .delete("/posts/{postId}")
                    .then()
                    .statusCode(200);
        }
    }
