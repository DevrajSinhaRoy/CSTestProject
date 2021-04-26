package utilities.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectToJson {
    ObjectMapper objectMapper = new ObjectMapper();

    public String serializeToJson(Object obj) throws JsonProcessingException {
         return objectMapper.writeValueAsString(obj);
    }
}
