package utilities.restAssuredExtension;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import org.junit.Assert;
import utilities.FrameworkException;
import utilities.LoggerPrinter;
import utilities.OutputMode;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ApiResponseValidator {
    public static void assertStatusCode(Response response, int expectedStatus){
        Allure.step("Validating Status Code is: "+expectedStatus);
        int actualStatus =  response.getStatusCode();
        LoggerPrinter.print("Status Code Validation ExpectedStatus: "+expectedStatus+" ActualStatus: "+actualStatus, OutputMode.BOTH);
        Assert.assertEquals("Status code mismatch",expectedStatus,actualStatus);
    }
    public static void assertFieldEquals(Response response, String jsonPath, String expectedValue){
        String actualValue = response.jsonPath().getString(jsonPath);
        Allure.step("Validating JSON field '"+jsonPath+"' = '"+expectedValue+"'");
        LoggerPrinter.print("Validating field value for the jsonPath: "+jsonPath+" Expected: "+expectedValue, OutputMode.BOTH);
        Assert.assertEquals("Field value mismatch",expectedValue,actualValue);
    }
    public static void assertBodyContains(Response response, String text){
        Allure.step("checking if body contains text '"+text+"'");
        LoggerPrinter.print("Validating Response Body contains Text: "+text, OutputMode.BOTH);
        Assert.assertTrue("Response body does not contain expected text '"+text+"'",response.getBody().toString().contains(text));
    }

    public static void assertBodyDoesNotContains(Response response, String searchText){
        List<String> searchList = Arrays.asList(searchText.split(","));
        List<String> failList = new ArrayList<>();
        String body = response.getBody().toString();
        for(String item :searchList)
            if (body.contains(item))
                failList.add(item);
        Allure.step("Checking if body doesn't contain any items from the list : "+searchList.toString());
        LoggerPrinter.print("Checking if body doesn't contain any items from the list : "+searchList.toString(),OutputMode.BOTH);
        Assert.assertTrue("Response body contains following elements from the input list : "+failList.toString(), failList.isEmpty());
    }
    public static void assertJsonSchema(Response response, String schemaPath){
        try {
            String schema = new String(Files.readAllBytes(Paths.get(schemaPath)), StandardCharsets.UTF_8);
            Allure.step("Validating JSON body against schema: "+schemaPath);
            LoggerPrinter.print("Validating JSON body against schema: "+schemaPath,OutputMode.BOTH);
            response.then().assertThat().body(matchesJsonSchema(schema));
            Allure.addAttachment("JSON schema used","application/json", new ByteArrayInputStream(schema.getBytes(StandardCharsets.UTF_8)),".json");
        } catch (Exception e) {
            LoggerPrinter.print("Failed to validate JSON schema: "+schemaPath,OutputMode.LOG);
            throw new FrameworkException("Failed to validate JSON schema: "+schemaPath,e);
        }
    }

    private static void logStep(String description){
        Allure.step(description);
    }
}
