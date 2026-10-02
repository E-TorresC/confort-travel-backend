package com.conforttravel.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.TimeZone;

@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Registra el módulo para manejar LocalDateTime, OffsetDateTime, etc.
        mapper.registerModule(new JavaTimeModule());

        // Fuerza a Jackson a usar UTC para todas las fechas
        mapper.setTimeZone(TimeZone.getTimeZone("UTC"));

        // Desactiva la escritura de fechas como timestamps numéricos
        mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }
}