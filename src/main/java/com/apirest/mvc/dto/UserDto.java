package com.apirest.mvc.dto;

import com.apirest.mvc.dto.support.AuditableDto;
import com.apirest.mvc.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends AuditableDto<Long, UserDto> {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String fullname;

	@NotEmpty
	private String username;

	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	private String password;

	@ApiModelProperty(example = "READER")
	private UserType userType;

}