package com.lee.owner.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.UnknownFormatConversionException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author joseph.li
 * @date 2020/5/6 6:40 下午
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JackSonUtils {

    private static final int MAX_OBJECT_MAPPER_COUNT = 8;

    public static ObjectMapper getObjectMapper() {
        int position = ThreadLocalRandom.current().nextInt(MAX_OBJECT_MAPPER_COUNT);
        return JackSonUtilsHolder.OBJECT_MAPPERS[position];
    }

    public static <T> T readValue(String json, Class<T> tClazz) {
        try {
            return getObjectMapper().readValue(json, tClazz);
        } catch (IOException e) {
            log.error("string read to object error", e);
            throw new UnknownFormatConversionException("string read to object error");
        }
    }

    public static String writeValueAsString(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("object write to string error", e);
            throw new UnknownFormatConversionException("object write to string error");
        }
    }

    @SuppressWarnings({"rawtypes" })
    public static <T> T readValue(String jsonStr, TypeReference tr) {
        try {
            return getObjectMapper().readValue(jsonStr, tr);
        } catch (IOException e) {
            log.error("read to JsonNode error", e);
            throw new UnknownFormatConversionException("read to JsonNode error");
        }
    }
    public static JsonNode readTree(String json) {
        try {
            return getObjectMapper().readTree(json);
        } catch (IOException e) {
            log.error("read to JsonNode error", e);
            throw new UnknownFormatConversionException("read to JsonNode error");
        }
    }

    public static ObjectNode createObjectNode() {
        return getObjectMapper().createObjectNode();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class JackSonUtilsHolder {

        private static ObjectMapper[] OBJECT_MAPPERS= new ObjectMapper[MAX_OBJECT_MAPPER_COUNT];

        static {
            ObjectMapper objectMapper;
            for (int i = 0; i < MAX_OBJECT_MAPPER_COUNT; i++) {
                objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

                OBJECT_MAPPERS[i] = objectMapper;
            }
        }

    }

}
