package com.apirest.mvc.service;

import com.apirest.mvc.dto.UserDto;
import com.apirest.mvc.model.User;
import com.apirest.mvc.repository.UserRepository;
import com.apirest.mvc.service.support.CrudServiceImpl;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService extends CrudServiceImpl<UserRepository, User, Long, UserDto> {

	public UserService(UserRepository repository, ModelMapper modelMapper) {
		super(repository, User.class, UserDto.class, modelMapper);
	}

}