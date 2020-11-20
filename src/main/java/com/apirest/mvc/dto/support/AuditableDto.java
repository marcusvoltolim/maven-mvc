package com.apirest.mvc.dto.support;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AuditableDto<I,U> extends PersistableDto<I> {

	private static final long serialVersionUID = 1L;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Nullable
	private U createdBy;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Nullable
	private LocalDateTime createdDate;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Nullable
	private U lastModifiedBy;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Nullable
	private LocalDateTime lastModifiedDate;

}