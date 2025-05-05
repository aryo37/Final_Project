package helper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.util.Random;

public class Models {

    private static RequestSpecification request;

    public static void setupHeaders() {
        request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "63a804408eb0cb069b57e43a");
    }

    public static Response getListUsers(String endpoint) {
        setupHeaders();
        return request.when().get(endpoint);
    }

    public static Response postCreateUser(String endpoint) {
        String title = "mr";
        String firstname = "Victor";
        String lastname = "Eduardo";
        JSONObject payload = new JSONObject();
        payload.put("title", title);
        payload.put("firsname", firstname);
        payload.put("lastname", lastname);

        setupHeaders();
        return request.body(payload.toString()).when().post(endpoint);
    }

    public static Response deleteUser(String url) {
        setupHeaders();
        return request.delete(url);
    }

    public static String generateRandomEmail() {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder email = new StringBuilder();

        // Generate a random string for the email prefix
        for (int i = 0; i < 10; i++) {
            email.append(characters.charAt(random.nextInt(characters.length())));
        }

        // Append a domain to the email
        email.append("@example.com");

        return email.toString();
    }
}
