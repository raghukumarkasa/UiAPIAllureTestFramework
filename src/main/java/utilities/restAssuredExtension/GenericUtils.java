package utilities.restAssuredExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Map;

public class GenericUtils {
    public static String prettyPrintJsonFromMap(Map<String,?> jsonString){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String outputJson=null;
        try {
            outputJson = mapper.writeValueAsString(jsonString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return outputJson;
    }
}
