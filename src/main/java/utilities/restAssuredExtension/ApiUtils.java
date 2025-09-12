package utilities.restAssuredExtension;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import utilities.FrameworkException;
import utilities.LoggerPrinter;
import utilities.OutputMode;

public class ApiUtils {

    private static RequestSpecBuilder reqSpecBuilder =new RequestSpecBuilder();

    public static Response get(String baseUri, String endPoint, Map<String,String> headers, Map<String,?> pathParams, Map<String,?> queryParams) {
        LoggerPrinter.print("Sending GET Request to "+baseUri+endPoint+" with pathParams: " + pathParams,OutputMode.BOTH);
        Response response;

        try {
            response = performRequest("GET",baseUri, endPoint, headers, pathParams, queryParams, null);
            //LoggerPrinter.print("Received Response : " + response.toString(),OutputMode.DEBUG);
        } catch (Exception e) {
            LoggerPrinter.print("Error during GET:"+e.getMessage(),OutputMode.LOG);
            throw new FrameworkException("GET request failed",e);
        }
        return response;
    }

    public static Map<String, String> generateAuthToken(String token) {
        Map<String, String> authHeaders = new HashMap<String, String>();
        authHeaders.put("Authorization", "Bearer "+token);
        return authHeaders;
    }

    public static Response post(String baseUri, String endPoint, Map<String,String> headers, Map<String, Object> body) {
        LoggerPrinter.print("Sending POST Request to "+baseUri+endPoint+" with Body: " + body,OutputMode.BOTH);
        Response response;
        try {
            response =  performRequest("POST",baseUri, endPoint, headers, null,null, body);
        } catch (Exception e) {
            LoggerPrinter.print("Error during POST:"+e.getMessage(),OutputMode.LOG);
            throw new FrameworkException("POST Request failed",e);
        }
        return response;
    }

    private static Response performRequest(String method, String baseUri, String endPoint, Map<String,String> headers, Map<String,?> pathParams, Map<String,?> queryParams, Map<String,?> body){
        reqSpecBuilder.setBaseUri(baseUri);
        reqSpecBuilder.setRelaxedHTTPSValidation();
        reqSpecBuilder.setContentType(ContentType.JSON);

        if (headers!=null) reqSpecBuilder.addHeaders(headers);
        if (pathParams!=null) reqSpecBuilder.addPathParams(pathParams);
        if (queryParams!=null) reqSpecBuilder.addQueryParams(queryParams);
        if (body!=null) reqSpecBuilder.setBody(body);

        RequestSpecification reqSpec = reqSpecBuilder.build();
        SpecificationLogger specLogger = new SpecificationLogger();
        reqSpec.filter(RequestLoggingFilter.logRequestTo(specLogger.getStream("Request Data")));
        reqSpec.filter(ResponseLoggingFilter.logResponseTo(specLogger.getStream("Response Data")));


        Response response;

        try {
            switch (method.trim().toUpperCase()){
                case "GET": response = RestAssured.given(reqSpec).get(endPoint);break;
                case "POST": response = RestAssured.given(reqSpec).post(endPoint);break;
                case "PUT": response = RestAssured.given(reqSpec).put(endPoint);break;
                case "PATCH": response = RestAssured.given(reqSpec).patch(endPoint);break;
                case "DELETE": response = RestAssured.given(reqSpec).delete(endPoint);break;
                default: throw new IllegalArgumentException("Unsupported method: " + method);
            }
        } catch (Exception e) {
            throw new FrameworkException("Error while executing API ",e);
        }
        Allure.addAttachment("API/Request","text/plain", new ByteArrayInputStream(buildRequestLog(method, baseUri, endPoint, headers, pathParams, queryParams, (body == null ? "No Body" : GenericUtils.prettyPrintJsonFromMap(body))).getBytes(StandardCharsets.UTF_8)),".txt");
        Allure.addAttachment("API/Response","text/plain", new ByteArrayInputStream(buildResponseLog(response).getBytes(StandardCharsets.UTF_8)),".txt");

        reqSpecBuilder = new RequestSpecBuilder();
        return response;
    }

    private static String buildRequestLog(String method, String baseUri, String endpoint,Map<String,String> headers, Map<String,?> pathParams, Map<String,?> queryParams, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append(method).append(" ").append(baseUri);
        // Replace path params placeholders in endpoint .
        if(pathParams!=null) {
            for (Object key : pathParams.keySet()) {
                String placeHolder = "{" + key + "}";
                if (endpoint.contains(placeHolder))
                    endpoint = endpoint.replace(placeHolder, pathParams.get(key).toString());
            }
            sb.append(endpoint).append("\n");
        }
        if(headers!=null){
            sb.append("Headers:").append("\n");
            headers.forEach((k,v)->sb.append(k).append(":").append(v).append("\n"));
        }
        if(queryParams!=null) {
            sb.append("QueryParams:").append("\n");
            queryParams.forEach((k, v) -> sb.append(k).append(":").append(v).append("\n"));
        }
        if(body!=null)
            sb.append("\nBody:\n").append(body.toString()).append("\n");

        return sb.toString();
    }

    private static String buildResponseLog(Response response){
        StringBuilder sb = new StringBuilder();
        sb.append("Status Code: ").append(response.getStatusCode()).append("\n");
        response.getHeaders().forEach(h->sb.append(h.getName()).append(":").append(h.getValue()).append("\n"));
        sb.append("\nBody:\n").append(response.getBody().asPrettyString()).append("\n");
        return sb.toString();
    }
}
