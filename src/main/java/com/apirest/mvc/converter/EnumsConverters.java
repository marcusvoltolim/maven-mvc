package com.apirest.mvc.converter;

import com.apirest.mvc.enums.UserType;
import com.apirest.mvc.enums.support.PersistableEnum;

import java.util.Objects;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import lombok.NonNull;

@SuppressWarnings("unused")
public interface EnumsConverters {

	abstract class AbstractPersistableEnumConverter<E extends Enum<E> & PersistableEnum<I>, I> implements AttributeConverter<E, I> {

		private final E[] enumConstants;

		public AbstractPersistableEnumConverter(@NonNull Class<E> enumType) {
			enumConstants = enumType.getEnumConstants();
		}

		@Override
		public I convertToDatabaseColumn(E attribute) {
			return Objects.isNull(attribute) ? null : attribute.getId();
		}

		@Override
		public E convertToEntityAttribute(I dbData) {
			return Objects.isNull(dbData) ? null : fromId(dbData);
		}

		private E fromId(@NonNull I id) {
			return Stream.of(enumConstants)
						 .filter(e -> e.getId().equals(id))
						 .findAny()
						 .orElse(null);
		}

	}

	@Converter(autoApply = true)
	class UserTypeConverter extends AbstractPersistableEnumConverter<UserType, Integer> {

		public UserTypeConverter() {
			super(UserType.class);
		}

	}

}