package com.apirest.mvc.service.support;

import com.apirest.mvc.dto.support.PersistableDto;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<I, D extends PersistableDto<I>> {

	D create(D dto);

	D update(D dto);

	Optional<D> findById(I id);

	Page<D> findAll(Pageable pageable);

	boolean deleteById(I id);

}