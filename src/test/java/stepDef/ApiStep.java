package stepDef;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import org.json.JSONObject;
import pages.ApiPage;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ApiStep {
    ApiPage apiPage;

    private String endpoint;

    private Response response;

    private String requestPayload;

    public ApiStep(){
        this.apiPage = new ApiPage();
    }

    @Given("prepare url valid for {string}")
    public void prepareUrlValidFor(String endpoint) {
        apiPage.prepareUrlValidFor(endpoint);
    }

    @And("hit api get list users")
    public void hitApiGetListUsers() { apiPage.hitApiGetListUsers();
    }

    @Then("validation status code is equals {int}")
    public void validationStatusCodeIsEquals(int status_code) {
        apiPage.validationStatusCodeIsEquals(status_code);
    }

    @Then("validation response body get list users")
    public void validationResponseBodyGetListUsers() {
        apiPage.validationResponseBodyGetListUsers();
    }

    @Then("validation response json with JSONSchema {string}")
    public void validationResponseJsonWithJSONSchema(String filename) {
        apiPage.validationResponseJsonWithJSONSchema(filename);
    }

    @When("hit api get user")
    public void hitApiGetUser() {
        apiPage.hitApiGetUser();
    }

    @Given("prepare url valid for get user by id {string}")
    public void prepareUrlValidForGetUserById(String userID) {
        this.endpoint = "https://dummyapi.io/data/v1/user/" + userID;
        apiPage.setEndpoint(endpoint);
        System.out.println("Endpoint yang digunakan: " + this.endpoint);
    }

    @Then("validation response body get user by id")
    public void validationResponseBodyGetUserById() {
        apiPage.validationResponseBodyGetUserById();
    }

    @When("hit api post create new user")
    public void hitApiPostCreateNewUser() {
        apiPage.hitApiPostCreateUser();
    }

    @Given("prepare valid request payload for new user")
    public void prepareValidRequestPayloadForNewUser() {
        apiPage.prepareValidRequestPayLoadForNewUser();
    }

    @Given("remove {string} field from payload")
    public void remove_field_from_payload(String field) {
        JSONObject payload = new JSONObject(requestPayload);
        payload.remove(field);
        this.requestPayload = payload.toString();
    }

    @And("validation response body create new user")
    public void validationResponseBodyCreateNewUser() {
        apiPage.validationResponseBodyCreateNewUser();
    }

    @And("hit api delete user")
    public void hitApiDeleteUser() {
        apiPage.hitApiDeleteUser();
    }
}
