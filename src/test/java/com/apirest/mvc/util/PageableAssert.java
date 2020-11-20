package com.apirest.mvc.util;

import org.assertj.core.api.AbstractAssert;

import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableAssert extends AbstractAssert<PageableAssert, Pageable> {

	PageableAssert(Pageable pageable) {
		super(pageable, PageableAssert.class);
	}

	public static PageableAssert assertThat(Pageable actual) {
		return new PageableAssert(actual);
	}

	public PageableAssert hasPageSize(int expectedPageSize) {
		if (!Objects.equals(actual.getPageSize(), expectedPageSize)) {
			failWithMessage("expected page size to be <%s> but was <%s>", expectedPageSize, actual.getPageSize());
		}
		return this;
	}

	public PageableAssert hasPageNumber(int expectedPageNumber) {
		if (!Objects.equals(actual.getPageNumber(), expectedPageNumber)) {
			failWithMessage("expected page number to be <%s> but was <%s>", expectedPageNumber, actual.getPageNumber());
		}
		return this;
	}

	public PageableAssert hasSort(String field, Sort.Direction direction) {
		var actualOrder = actual.getSort().getOrderFor(field);

		if (Objects.isNull(actualOrder)) {
			failWithMessage("expected sort for field <%s> to be <%s> but was null", field, direction);
		} else if (!Objects.equals(actualOrder.getDirection(), direction)) {
			failWithMessage("expected sort for field <%s> to be <%s> but was <%s>", field, direction, actualOrder.getDirection());
		}

		return this;
	}

}