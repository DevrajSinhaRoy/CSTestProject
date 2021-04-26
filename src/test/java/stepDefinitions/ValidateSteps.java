package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ApiClient;

import java.lang.reflect.InvocationTargetException;

public class ValidateSteps {
    ApiClient apiClient;

    @Given("^I have the \"(.*)\" domain$")
    public void setUpApi(String service) {
        apiClient = null;
        apiClient = new ApiClient(service);
    }

    @Given("^I have the \"(.*)\" api$")
    public void validUrl(String api) {
        apiClient.setRelativePath(api);
    }

    @And("^the header \"(.*)\" as \"(.*)\"$")
    public void setHeader(String key, String value) {
        apiClient.setHeader(key,value);
    }

    @When("^I hit the (.*) api$")
    public void validPayload(String requestType) {
        if (requestType.equalsIgnoreCase("post")) {
            apiClient.requestPost();
        }
        if (requestType.equalsIgnoreCase("put")) {
            apiClient.requestPut();
        }
        if (requestType.equalsIgnoreCase("get")) {
            apiClient.requestGet();
        }
    }

    @Then("^I get (.*) response code$")
    public void verifyCorrectResponse(int code) {
        apiClient.verify().statusCode(code);
    }

    @When("^I set (.*) as \"(.*)\"$")
    public void updateJsonKey(String key, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        apiClient.setJsonValue(key, value);
    }

    @And("^I get the (.*) as \"(.*)\"$")
    public void verifyResponseValues(String key, String value) {
        apiClient.verify().parameterValue(key, value);
    }

    @And("^I get the (.*) as \"(.*)\" for (.*) request$")
    public void verifyResponseValue(String keys, String values, int indexNumber) {
        String key[] = keys.split(";");
        String value[] = values.split(";");
        apiClient.verify().parameterValue(key, value, indexNumber);
    }

    @Given("^I update the existing body for (.*) as (.*)")
    public void updateExistingBody(String key, String value) {
        apiClient.updateExistingBody(key, value);
    }

    @And("^I create a new entity with (.*) as \"(.*)\" for batch request$")
    public void addEntity(String keys, String values) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String key[] = keys.split(";");
        String value[] = values.split(";");
        apiClient.addEntity(key, value);
    }

    @After
    public void cleanUp() {
        apiClient.clear();
    }
}
