package com.apirest.mvc.config;

import java.util.stream.Stream;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private static final int MAX_AGE = 4800;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.maxAge(MAX_AGE)
				.allowedMethods(Stream.of(HttpMethod.values()).map(Enum::name).toArray(String[]::new));
	}

}