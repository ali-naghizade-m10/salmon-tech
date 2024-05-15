import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class TopWordsInPosts {
        private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

        @BeforeAll
        public static void setUp() {
            RestAssured.baseURI = BASE_URL;
        }

        @Test
        public void testTopWordsInPosts() {
            Map<String, Integer> wordFrequency = new HashMap<>();

            for (int postId = 1; postId <= 100; postId++) {
                Response response = given()
                        .pathParam("postId", postId)
                        .when()
                        .get("/posts/{postId}")
                        .then()
                        .extract().response();

                String body = response.jsonPath().getString("body");
                String[] words = body.split("\\s+");

                for (String word : words) {
                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                }
            }

            wordFrequency.entrySet().stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                    .limit(10)
                    .forEachOrdered(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));
        }
    }
