package pages;

import helper.Endpoint;
import helper.Models;
import helper.Utility;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.File;
import java.util.List;

import static helper.Models.deleteUser;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class ApiPage {

    String setURL, global_id;

    Response res;

    private String endpoint;

    private String requestPayload;

    private static final String APP_ID = "63a804408eb0cb069b57e43a";

    public void prepareUrlValidFor(String endpoint) {
        switch (endpoint) {
            case "GET_LIST_USERS":
                setURL = Endpoint.GET_LIST_USERS;
                break;
            case "GET_USER_BY_ID":
                setURL = Endpoint.GET_USER_BY_ID;
                break;
            case "CREATE_NEW_USER":
                setURL = Endpoint.CREATE_NEW_USER;
                break;
            case "UPDATE_USER":
                setURL = Endpoint.UPDATE_USER;
                break;
            case "DELETE_USER":
                setURL = Endpoint.DELETE_USER;
                break;
            default:
                System.out.println("input right url");
        }
        System.out.println("endpoint siap pakai adalah : " + setURL);
    }

    public void prepareUrlValidForGetUserById(String userId) {
        this.setURL = Endpoint.GET_USER_BY_ID + "/" + userId;
        System.out.println("endpoint siap pakai adalah : " + setURL);
    }

    public void hitApiGetListUsers() {
        res = given()
                .header("app-id", APP_ID)
                .header("Content-Type", "application/json")
                .when()
                .get(setURL);
        System.out.println(res.getBody().asString());
    }

    public void validationResponseJsonWithJSONSchema(String filename) {
        File JSONFile = Utility.getJSONSchemaFile(filename);
        res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(JSONFile));
    }

    public void hitApiGetUser() {
        this.res = given()
                .header("app-id", APP_ID)
                .header("Content-Type", "application/json")
                .when()
                .get(this.endpoint);

        System.out.println("Response: " + res.getBody().asString());
    }

    public Response getResponse() {
        return this.res;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void prepareValidRequestPayLoadForNewUser() {
        String randomEmail = Models.generateRandomEmail();
        this.requestPayload = String.format("""
        {
               "title": "mr",
               "firstName": "Aryo",
               "lastName": "Prasetio",
               "email": "%s",
               "password": "P@ssw0rd123",
               "dateOfBirth": "1990-01-01",
               "phone": "+628123456789",
               "gender": "male"
        }
                """, randomEmail);
    }

    public void hitApiPostCreateUser() {
        res = given()
                .header("app-id", APP_ID)
                .header("Content-Type", "application/json")
                .body(requestPayload)
                .when()
                .post(Endpoint.CREATE_NEW_USER);

        System.out.println(res.getBody().asString());
    }

    public void validationStatusCodeIsEquals(int expectedStatusCode) {
        int actualStatusCode = res.getStatusCode();
        System.out.println("Response Body: " + res.getBody().asString());
        System.out.println("Actual Status Code: " + actualStatusCode);
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    public void validationStatusCode(int status_code) {
        assertThat(res.statusCode()).isEqualTo(status_code);
    }

    public void validationResponseBodyGetListUsers() {
        List<Object> id = res.jsonPath().getList("data.id");
        List<Object> title = res.jsonPath().getList("data.title");
        List<Object> firstName = res.jsonPath().getList("data.firstName");
        List<Object> lastName = res.jsonPath().getList("data.lastName");
        List<Object> picture = res.jsonPath().getList("data.picture");

        assertThat(id.get(0)).isNotNull();
        assertThat(title.get(0)).isNotNull();
        assertThat(firstName.get(0)).isNotNull();
        assertThat(lastName.get(0)).isNotNull();
        assertThat(picture.get(0)).isNotNull();

    }

    public void validationResponseBodyGetUserById() {
        String id = res.jsonPath().getString("id");
        String title = res.jsonPath().getString("title");
        String firstName = res.jsonPath().getString("firstName");
        String lastName = res.jsonPath().getString("lastName");
        String picture = res.jsonPath().getString("picture");
        String gender = res.jsonPath().getString("gender");
        String email = res.jsonPath().getString("email");
        String dateOfBirth = res.jsonPath().getString("dateOfBirth");
        String phone = res.jsonPath().getString("phone");
        String location = res.jsonPath().getString("location");

        assertThat(id).isNotNull();
        assertThat(title).isNotNull();
        assertThat(firstName).isNotNull();
        assertThat(lastName).isNotNull();
        assertThat(picture).isNotNull();
        assertThat(gender).isIn("female", "male");
        assertThat(email).isNotNull();
        assertThat(dateOfBirth).isNotNull();
        assertThat(phone).isNotNull();
        assertThat(location).isNotNull();

        global_id = id;
    }

    public void validationResponseBodyCreateNewUser() {

        assertThat(res).isNotNull();

        assertThat(res.jsonPath().getString("id"))
                .as("ID should not be empty")
                .isNotNull()
                .isNotEmpty();

        assertThat(res.jsonPath().getString("firstName"))
                .as("First name should not be empty")
                .isNotNull()
                .isNotEmpty();

        assertThat(res.jsonPath().getString("lastName"))
                .as("Last name should not be empty")
                .isNotNull()
                .isNotEmpty();
    }

    public void hitApiDeleteUser() {

        if (global_id == null) {
            throw new IllegalStateException("User  ID to delete is not set.");
        }

        res = given()
                .header("app-id", APP_ID)
                .header("Content-Type", "application/json")
                .when()
                .delete(Endpoint.DELETE_USER.replace("60d0fe4f5311236168a109cf", global_id));
        System.out.println("Delete status: " + res.getStatusCode());
        System.out.println("Response Body: " + res.getBody().asString());
    }
}

