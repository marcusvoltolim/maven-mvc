package com.apirest.mvc.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilsTest {

	@Test
	void getOnlyDigits() {
		assertEquals("001002", StringUtils.getOnlyDigits("001-002"));
	}

}