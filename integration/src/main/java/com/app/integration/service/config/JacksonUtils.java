package com.app.integration.service.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.camel.component.jackson.JacksonDataFormat;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JacksonUtils {

    private final ObjectMapper objectMapper;

    public JacksonUtils() {
        objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public JacksonDataFormat getJacksonDataFormat(Class<?> unmarshal, boolean useList) {
        JacksonDataFormat dataFormat = new JacksonDataFormat();

        if (useList) {
            dataFormat.useList();
        }
        dataFormat.setUnmarshalType(unmarshal);
        dataFormat.setObjectMapper(objectMapper);

        return dataFormat;
    }
}
