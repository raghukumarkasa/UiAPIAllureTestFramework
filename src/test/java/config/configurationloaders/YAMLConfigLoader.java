package config.configurationloaders;

import org.yaml.snakeyaml.Yaml;
import utilities.FrameworkException;
import utilities.restAssuredExtension.AuthTokenManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YAMLConfigLoader {
    private static Map<String,String> config;
    static {
        try
        {
            String content =  Files.readString(Paths.get("src/test/resources/config.yaml"));
            content = replaceWithEnvValues(content);
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
    }

    public static String getUsernameForToken(){;
        return config.get("QP_API_TOKEN_USERNAME");
    }

    public static String getPasswordForToken(){
        return config.get("QP_API_TOKEN_PASSWORD");
    }

    public static String replaceWithEnvValues(String input) {
        Pattern pattern = Pattern.compile("\\$\\{([A-Za-z0-9_]+)\\}");
        Matcher matcher = pattern.matcher(input);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String var = matcher.group(1);
            String replacement = System.getenv(var) != null ? System.getenv(var) : "";
            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
