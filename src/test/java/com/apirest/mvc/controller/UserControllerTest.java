package com.apirest.mvc.controller;

import com.apirest.mvc.dto.UserDto;
import com.apirest.mvc.service.UserService;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.apirest.mvc.util.TestUtils.getUserDto;
import static com.apirest.mvc.util.TestUtils.writeJson;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(UserController.class)
//include @ExtendWith(SpringExtension.class) in @WebMvcTest
class UserControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserService service;

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void create() throws Exception {
		given(service.create(any(UserDto.class))).willAnswer(invocation -> invocation.getArgument(0));

		mvc.perform(post("/api/v1/user").contentType(MediaType.APPLICATION_JSON).content(writeJson(getUserDto(null))))
		   .andExpect(status().isCreated())
		   .andExpect(jsonPath("$", aMapWithSize(9)))
		   .andExpect(jsonPath("$.id").value(IsNull.nullValue()))
		   .andExpect(jsonPath("$.fullname", is("Marcus Vinicius Voltolim")))
		   .andExpect(jsonPath("$.username", is("marcus.voltolim")))
		   .andExpect(jsonPath("$.password", is("12345")));

		verify(service).create(any(UserDto.class));
		verifyNoMoreInteractions(service);
	}

	@Test
	void create_WithId() throws Exception {
		var userDto = getUserDto(1L);

		mvc.perform(post("/api/v1/user").contentType(MediaType.APPLICATION_JSON).content(writeJson(userDto)))
		   .andExpect(status().isBadRequest())
		   .andExpect(jsonPath("$.errorMsg", is("userDto -> [id: deve ser nulo]")));

		verifyNoInteractions(service);
	}

	@Test
	void create_WithoutRequiredFields() throws Exception {
		var userDto = getUserDto(null);
		userDto.setUsername("");
		userDto.setFullname(null);

		mvc.perform(post("/api/v1/user").contentType(MediaType.APPLICATION_JSON).content(writeJson(userDto)))
		   .andExpect(status().isBadRequest())
		   .andExpect(jsonPath("$.errorMsg", is("userDto -> [fullname: não deve estar vazio, username: não deve estar vazio]")));

		verifyNoInteractions(service);
	}

	@Test
	void update() throws Exception {
		given(service.update(any(UserDto.class))).willAnswer(invocation -> invocation.getArgument(0));


		mvc.perform(put("/api/v1/user").contentType(MediaType.APPLICATION_JSON).content(writeJson(getUserDto(1L))))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$", aMapWithSize(9)))
		   .andExpect(jsonPath("$.id", is(1)))
		   .andExpect(jsonPath("$.fullname", is("Marcus Vinicius Voltolim")))
		   .andExpect(jsonPath("$.username", is("marcus.voltolim")))
		   .andExpect(jsonPath("$.password", is("12345")));

		verify(service).update(any(UserDto.class));
		verifyNoMoreInteractions(service);
	}

	@Test
	void update_WithoutRequiredFields() throws Exception {
		var userDto = getUserDto(null);
		userDto.setUsername("");
		userDto.setFullname(null);

		mvc.perform(put("/api/v1/user").contentType(MediaType.APPLICATION_JSON).content(writeJson(userDto)))
		   .andExpect(status().isBadRequest())
		   .andExpect(jsonPath("$.errorMsg", is("userDto -> [fullname: não deve estar vazio, id: não deve ser nulo, username: não deve estar vazio]")));

		verifyNoInteractions(service);
	}

	@Test
	void getById() throws Exception {
		given(service.findById(1L)).willReturn(Optional.of(getUserDto(1L)));

		mvc.perform(get("/api/v1/user/1"))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$", aMapWithSize(9)))
		   .andExpect(jsonPath("$.id", is(1)))
		   .andExpect(jsonPath("$.fullname", is("Marcus Vinicius Voltolim")))
		   .andExpect(jsonPath("$.username", is("marcus.voltolim")))
		   .andExpect(jsonPath("$.password", is("12345")));

		verify(service).findById(1L);
		verifyNoMoreInteractions(service);
	}

	@Test
	void getById_NoExists() throws Exception {
		mvc.perform(get("/api/v1/user/1"))
		   .andExpect(status().isNotFound());

		verify(service).findById(1L);
		verifyNoMoreInteractions(service);
	}

	@Test
	void getAll() throws Exception {
		given(service.findAll(any(Pageable.class))).willReturn(PageableExecutionUtils.getPage(List.of(getUserDto(1L), getUserDto(2L)), Pageable.unpaged(), () -> 0));

		mvc.perform(get("/api/v1/user"))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$.number", is(0)))
		   .andExpect(jsonPath("$.totalElements", is(2)))
		   .andExpect(jsonPath("$.size", is(2)))
		   .andExpect(jsonPath("$.content", hasSize(2)))
		   .andExpect(jsonPath("$.content[0].id", is(1)))
		   .andExpect(jsonPath("$.content[0].fullname", is("Marcus Vinicius Voltolim")))
		   .andExpect(jsonPath("$.content[0].username", is("marcus.voltolim")))
		   .andExpect(jsonPath("$.content[0].password", is("12345")))
		   .andExpect(jsonPath("$.content[1].id", is(2)))
		   .andExpect(jsonPath("$.content[1].fullname", is("Marcus Vinicius Voltolim")))
		   .andExpect(jsonPath("$.content[1].username", is("marcus.voltolim")))
		   .andExpect(jsonPath("$.content[1].password", is("12345")));

		verify(service).findAll(any(Pageable.class));
		verifyNoMoreInteractions(service);
	}

	@Test
	void deleteById() throws Exception {
		given(service.deleteById(1L)).willReturn(true);

		mvc.perform(delete("/api/v1/user/1"))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$", is(true)));

		verify(service).deleteById(1L);
		verifyNoMoreInteractions(service);
	}

}