package com.apirest.mvc.service;

import com.apirest.mvc.config.ModelMapperConfig;
import com.apirest.mvc.dto.UserDto;
import com.apirest.mvc.exception.CustomRuntimeException;
import com.apirest.mvc.model.User;
import com.apirest.mvc.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

import static com.apirest.mvc.util.TestUtils.getUserDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository repository;

	@Spy
	private final ModelMapper modelMapper = ModelMapperConfig.getModelMapper();

	@InjectMocks
	private UserService service;

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void create() {
		given(repository.saveAndFlush(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));

		var expected = service.create(getUserDto(null));

		assertThat(expected).isInstanceOf(UserDto.class);
		verify(repository).saveAndFlush(any(User.class));
		verifyNoMoreInteractions(repository);
	}

	@Test
	void create_WithId() {
		var userDto = getUserDto(1L);
		var expected = assertThrows(CustomRuntimeException.class, () -> service.create(userDto));
		assertThat(expected).hasMessage("entity_with_id: [User]");

		verifyNoInteractions(repository);
	}

	@Test
	void update() {
		given(repository.saveAndFlush(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));
		given(repository.existsById(any(Long.class))).willReturn(true);

		var expected = service.update(getUserDto(1L));

		assertThat(expected).isInstanceOf(UserDto.class);
		verify(repository).saveAndFlush(any(User.class));
		verifyNoMoreInteractions(repository);
	}

	@Test
	void update_WithoutId() {
		var userDto = getUserDto(null);
		var expected = assertThrows(CustomRuntimeException.class, () -> service.update(userDto));
		assertThat(expected).hasMessage("entity_without_id: [User]");

		verifyNoInteractions(repository);
	}

	@Test
	void update_EntityNoExists() {
		given(repository.existsById(any(Long.class))).willReturn(false);

		var userDto = getUserDto(1L);
		var expected = assertThrows(CustomRuntimeException.class, () -> service.update(userDto));
		assertThat(expected).hasMessage("entity_no_exists: [User]");

		verifyNoMoreInteractions(repository);
	}

	@Test
	void findById() {
		given(repository.findById(any(Long.class))).willReturn(Optional.of(new User()));

		var expected = service.findById(1L).orElse(null);
		assertThat(expected).isInstanceOf(UserDto.class);

		verifyNoMoreInteractions(repository);
	}

	@Test
	void findAll() {
		given(repository.findAll(any(Pageable.class))).willReturn(PageableExecutionUtils.getPage(List.of(new User(), new User()), Pageable.unpaged(), () -> 0));

		var expected = service.findAll(Pageable.unpaged());
		assertThat(expected)
			.isNotEmpty()
			.hasSize(2)
			.hasOnlyElementsOfType(UserDto.class);

		verifyNoMoreInteractions(repository);
	}

	@Test
	void deleteById() {
		var expected = service.deleteById(1L);
		assertThat(expected).isTrue();

		verify(repository).deleteById(any(Long.class));
		verifyNoMoreInteractions(repository);
	}

}