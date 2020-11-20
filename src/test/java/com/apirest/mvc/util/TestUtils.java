package com.apirest.mvc.util;

import com.apirest.mvc.dto.UserDto;
import com.apirest.mvc.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class TestUtils {

	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private TestUtils() {}

	public static String writeJson(final Object obj) throws IOException {
		return OBJECT_MAPPER.writeValueAsString(obj);
	}

	public static <T> T readJson(byte[] bytes, Class<T> type) throws IOException {
		return OBJECT_MAPPER.readValue(bytes, type);
	}

	public static <T> T readJson(byte[] bytes, TypeReference<T> type) throws IOException {
		return OBJECT_MAPPER.readValue(bytes, type);
	}

	public static User getUser(Long id) {
		var user = new User();
		user.setFullname("Marcus Vinicius Voltolim");
		user.setUsername("marcus.voltolim");
		user.setPassword("12345");
		return user;
	}

	public static UserDto getUserDto(Long id) {
		var userDto = new UserDto();
		userDto.setFullname("Marcus Vinicius Voltolim");
		userDto.setUsername("marcus.voltolim");
		userDto.setPassword("12345");
		userDto.setId(id);
		return userDto;
	}

}