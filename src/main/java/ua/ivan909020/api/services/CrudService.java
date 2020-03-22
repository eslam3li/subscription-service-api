package ua.ivan909020.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

interface CrudService<T> {

	boolean existsById(Integer id);

	T findById(Integer id);

	T create(T entity);

	T update(T entity);

	void deleteById(Integer id);

	Page<T> findAll(Pageable pageable);

}
