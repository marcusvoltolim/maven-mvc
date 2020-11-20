package com.apirest.mvc.controller;

import com.apirest.mvc.controller.support.CrudController;
import com.apirest.mvc.dto.UserDto;
import com.apirest.mvc.service.UserService;
import com.apirest.mvc.util.Constants;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(Constants.API_V_1 + "/user")
@RestController
public class UserController extends CrudController<UserService, Long, UserDto> {

	public UserController(UserService service) {
		super(service);
	}

}