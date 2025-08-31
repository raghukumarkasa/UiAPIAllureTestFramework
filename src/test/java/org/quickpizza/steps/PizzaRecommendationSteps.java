package org.quickpizza.steps;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.http.Header;
import io.restassured.response.Response;
import utilities.restAssuredExtension.ApiUtils;
import utilities.restAssuredExtension.ApiResponseValidator;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PizzaRecommendationSteps {

    private static final String BASE_URL = "https://quickpizza.grafana.com";
    private static Header header;

//    headers.("Authorization", "Bearer ");

    private Response response;




    @When("I send a GET request to {string} for id {int}")
    public void iSendAGETRequestTo(String endPoint, int id) {
        Map<String, Integer> pathParams = new HashMap<String, Integer>();
        pathParams.put("id",id);

        response = ApiUtils.get(BASE_URL, endPoint, ApiUtils.generateAuthToken(), pathParams,null);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        ApiResponseValidator.assertStatusCode(response, statusCode);
    }

    @Then("the JSON Response body matches the user schema {string}")
    public void theJsonResponseBodyMatchesSchema(String schema) {
        String schemaPath = "src/test/resources/InputDataFiles/schemas/"+schema;
        ApiResponseValidator.assertJsonSchema(response, schemaPath);
    }


    @When("I send a POST request to {string} with body as {string}")
    public void iSendAPOSTRequestToWithBodyAs(String endPoint, String jsonFileName) {
        String jsonFilePath = "src/test/resources/InputDataFiles/InputJsonFiles/"+jsonFileName;
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> jsonBody = null;
        try{
            jsonBody = mapper.readValue(new File(jsonFilePath),Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response = ApiUtils.post(BASE_URL, endPoint, ApiUtils.generateAuthToken(), jsonBody);
    }

    @Then("the pizza is vegetarian")
    public void thePizzaIsVegetarian() {
        ApiResponseValidator.assertFieldEquals(response,"vegetarian","true");
    }

    @And("the response excludes ingredients {string}")
    public void theResponseExcludesIngredients(String ingredients) {
        ApiResponseValidator.assertBodyDoesNotContains(response,ingredients);
    }
}

