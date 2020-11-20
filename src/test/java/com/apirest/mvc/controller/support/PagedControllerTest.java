package com.apirest.mvc.controller.support;

import com.apirest.mvc.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.apirest.mvc.util.PageableAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(CrudController.class)
//include @ExtendWith(SpringExtension.class) in @WebMvcTest
class PagedControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService service;

	@Test
	void evaluatesPageableDefault() throws Exception {
		mockMvc.perform(get("/api/v1/user"))
			   .andExpect(status().isOk());

		var captor = ArgumentCaptor.forClass(Pageable.class);
		verify(service).findAll(captor.capture());

		var pageable = (PageRequest) captor.getValue();
		assertThat(pageable).hasPageNumber(0);
		assertThat(pageable).hasPageSize(10);
		assertThat(pageable).hasSort("id", Sort.Direction.ASC);
	}

	@Test
	void evaluatesPageableParameter() throws Exception {
		mockMvc.perform(get("/api/v1/user")
							.param("page", "5")
							.param("size", "20")
							.param("sort", "id,desc") // <-- no space after comma!!!
							.param("sort", "username,asc")) // <-- no space after comma!!!
			   .andExpect(status().isOk());

		var captor = ArgumentCaptor.forClass(Pageable.class);
		verify(service).findAll(captor.capture());
		var pageable = (PageRequest) captor.getValue();

		assertThat(pageable).hasPageNumber(5);
		assertThat(pageable).hasPageSize(20);
		assertThat(pageable).hasSort("username", Sort.Direction.ASC);
		assertThat(pageable).hasSort("id", Sort.Direction.DESC);
	}

	@Test
	void setsUpperPageLimit() throws Exception {
		mockMvc.perform(get("/api/v1/user").param("size", "10000"))
			   .andExpect(status().isOk());

		var captor = ArgumentCaptor.forClass(Pageable.class);
		verify(service).findAll(captor.capture());
		var pageable = (PageRequest) captor.getValue();

		assertThat(pageable).hasPageSize(2000);
	}

	@Test
	void evaluatesSortParameter() throws Exception {
		mockMvc.perform(get("/api/v1/user").param("sort", "username,desc")) // <-- no space after comma!!!
			   .andExpect(status().isOk());

		var captor = ArgumentCaptor.forClass(Pageable.class);
		verify(service).findAll(captor.capture());

		var pageable = captor.getValue();
		assertThat(pageable).hasPageNumber(0);
		assertThat(pageable).hasPageSize(10);
		assertThat(pageable).hasSort("username", Sort.Direction.DESC);
	}

}