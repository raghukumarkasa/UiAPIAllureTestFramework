package config.configurationloaders;

import org.yaml.snakeyaml.Yaml;
import utilities.FrameworkException;


import java.io.InputStream;
import java.util.Map;

public class YAMLConfigLoader {
    private static Map<String,String> config;

    static {
        try
        {
            InputStream in = YAMLConfigLoader.class.getClassLoader().getResourceAsStream("config.yaml");
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
        return config.get("API_AUTH_TOKEN");
    }
}
