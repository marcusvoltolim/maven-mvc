package com.apirest.mvc.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ValidationMsg {

	ENTITY_WITH_ID("entity_with_id"),
	ENTITY_WITHOUT_ID("entity_without_id"),
	ENTITY_NO_EXISTS("entity_no_exists"),
	;

	private final String key;

}