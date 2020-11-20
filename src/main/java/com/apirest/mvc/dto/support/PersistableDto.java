package com.apirest.mvc.dto.support;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.Data;

@Data
public abstract class PersistableDto<I> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID da entidade")
	@Null(groups = ValidationGroups.Create.class)
	@NotNull(groups = ValidationGroups.Update.class)
	private I id;

}