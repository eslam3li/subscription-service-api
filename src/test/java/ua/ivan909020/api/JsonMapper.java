package ua.ivan909020.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonMapper {

    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonMapper() {
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

}
