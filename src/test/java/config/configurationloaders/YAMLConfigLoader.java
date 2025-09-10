package config.configurationloaders;

import org.yaml.snakeyaml.Yaml;
import utilities.FrameworkException;
import utilities.restAssuredExtension.AuthTokenManager;
import org.yaml.snakeyaml.Yaml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class YAMLConfigLoader {
    private static Map<String,String> config;
    static {
        try
        {
//            InputStream in = YAMLConfigLoader.class.getClassLoader().getResourceAsStream("config.yaml");
            String content =  Files.readString(Paths.get("src/test/resources/config.yaml"));
            content = replaceWithStaticEnvReferences(content);
            InputStream in = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
            Yaml yaml = new Yaml();
            config = yaml.load(in);
        } catch (Exception e) {
            throw new FrameworkException("Failed to load config.yaml",e);
        }
    }


    public static String getBaseUrl(){
        return config.get("API_BASE_URL");
    }

    public static String getAuthToken(){
        String username = getUsernameForToken();
        String password = getPasswordForToken();
        return AuthTokenManager.getToken(username,password);
        //return config.get("QP_API_AUTH_TOKEN");
    }

    public static String getUsernameForToken(){;
        return System.getenv("QP_API_TOKEN_USERNAME");
    }

    public static String getPasswordForToken(){
        return System.getenv("QP_API_TOKEN_PASSWORD");
    }

    public static String replaceWithStaticEnvReferences(String input) {
        return input.replaceAll("\\$\\{([A-Za-z0-9_]+)\\}", "System.getenv(\"$1\")");
    }
}
