package utilities.restAssuredExtension;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class AuthTokenManager {
    private static String accessToken = null;
    private static Instant expiry = null;
    private static final long REFRESH_BUFFER_SEC = 60;

    public static synchronized String getToken(String username, String password){
        if(accessToken==null || isExpired())
            refreshToken(username,password);
        return accessToken;
    }

    private static boolean isExpired(){
        return expiry==null||Instant.now().isAfter(expiry.minusSeconds(REFRESH_BUFFER_SEC));
    }

    private static void refreshToken(String username, String password){
        Map<String , String> loginPayload = new HashMap<>();
        String csrfToken = getCSRFToken();
        loginPayload.put("username", username);
        loginPayload.put("password", password);
        loginPayload.put("csrf",csrfToken);

        Response response = RestAssured.given()
                .contentType("application/json")
                .accept("application/json")
                .cookie("csrf_token",csrfToken)
                .body(loginPayload)
                .post("https://quickpizza.grafana.com/api/users/token/login?set_cookie=true");
        accessToken = response.jsonPath().getString("token");
        expiry = Instant.now().plusSeconds(15 * 60);
    }

    private static String getCSRFToken(){
        Response response = RestAssured.given()
                .post("https://quickpizza.grafana.com/api/csrf-token");
        return response.getCookie("csrf_token");
    }
}
