package com.apirest.mvc.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

	@Bean
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		var objectMapper = builder.build();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
		objectMapper.addMixIn(Object.class, IdFirstAndIgnoreHibernateProperties.class);
		return objectMapper;
	}

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "new"})
	@JsonPropertyOrder("id")
	private interface IdFirstAndIgnoreHibernateProperties {}

}