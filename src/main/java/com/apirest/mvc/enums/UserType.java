package com.apirest.mvc.enums;

import com.apirest.mvc.enums.support.PersistableEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType implements PersistableEnum<Integer> {

	ADMIN(1),
	READER(2),
	WRITER(3),
	READER_WRITER(4);

	private final Integer id;

	public static final UserType DEFAULT = READER;

}