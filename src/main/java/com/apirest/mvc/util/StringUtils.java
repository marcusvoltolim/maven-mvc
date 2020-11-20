package com.apirest.mvc.util;

import org.springframework.lang.Nullable;

public final class StringUtils {

	private StringUtils() {}

	public static String getOnlyDigits(@Nullable String s) {
		return s != null ? s.replaceAll("\\D", "") : null;
	}

}