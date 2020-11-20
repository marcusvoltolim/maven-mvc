package com.apirest.mvc.service.support;

import com.apirest.mvc.dto.support.PersistableDto;
import com.apirest.mvc.exception.CustomRuntimeException;
import com.apirest.mvc.exception.ValidationMsg;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CrudServiceImpl<R extends JpaRepository<T, I>, T extends Persistable<Long>, I, D extends PersistableDto<I>> implements CrudService<I, D> {

	protected final R repository;

	protected final Class<T> entityClass;

	protected final String entityName;

	protected final Class<D> dtoClass;

	protected final ModelMapper modelMapper;

	public CrudServiceImpl(@NonNull R repository, @NonNull Class<T> entityClass, @NonNull Class<D> dtoClass, @NonNull ModelMapper modelMapper) {
		this.repository = repository;
		this.entityClass = entityClass;
		this.entityName = entityClass.getSimpleName();
		this.dtoClass = dtoClass;
		this.modelMapper = modelMapper;
	}

	@Transactional
	@Override
	public D create(@NonNull D dto) {
		throwsException(Objects.isNull(dto.getId()), ValidationMsg.ENTITY_WITH_ID);

		var entity = convertToEntity(dto);
		var dtoNewEntity = convertToDto(repository.saveAndFlush(entity));
		logInfo("Create '{}' with ID: '{}'", entityName, dtoNewEntity.getId());
		return dtoNewEntity;
	}

	@Transactional
	@Override
	public D update(@NonNull D dto) {
		throwsException(Objects.nonNull(dto.getId()), ValidationMsg.ENTITY_WITHOUT_ID);
		throwsException(repository.existsById(dto.getId()), ValidationMsg.ENTITY_NO_EXISTS);

		var entity = convertToEntity(dto);
		logInfo("Update '{}' with ID: '{}'", entityName, dto.getId());
		return convertToDto(repository.saveAndFlush(entity));
	}

	@Override
	public Optional<D> findById(I id) {
		logInfo("Find '{}' with ID: '{}'", entityName, id);
		return repository.findById(id).map(this::convertToDto);
	}

	@Override
	public Page<D> findAll(@NonNull Pageable pageable) {
		logInfo("Find all {} with pagiation: {}", entityName + "s", pageable);
		return repository.findAll(pageable).map(this::convertToDto);
	}

	@Transactional
	@Override
	public boolean deleteById(I id) {
		logInfo("Delete '{}' with ID: '{}'", entityName, id);
		repository.deleteById(id);
		return true;
	}

	protected D convertToDto(@NonNull T entity) {
		return modelMapper.map(entity, dtoClass);
	}

	protected T convertToEntity(@NonNull D dto) {
		return modelMapper.map(dto, entityClass);
	}

	protected void throwsException(boolean condition, ValidationMsg msgFailCondition) {
		if (!condition) {
			throw new CustomRuntimeException(msgFailCondition, entityName);
		}
	}

	protected static void logInfo(@NonNull String format, Object... args) {
		log.info(format + " (" + LocalDateTime.now() + ")", args);
	}

}