package ua.ivan909020.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.ivan909020.api.domain.dao.User;
import ua.ivan909020.api.exceptions.ValidationException;
import ua.ivan909020.api.repositories.UserRepository;
import ua.ivan909020.api.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository repository;

	@Autowired
	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public boolean existsById(Integer userId) {
		if (userId == null) {
			throw new IllegalArgumentException("User ID must not be NULL");
		}
		return repository.existsById(userId);
	}

	@Override
	public User findById(Integer userId) {
		if (userId == null) {
			throw new IllegalArgumentException("User ID must not be NULL");
		}
		return repository.findById(userId).orElse(null);
	}

	@Override
	public User create(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User must not be NULL");
		}
		if (user.getId() != null) {
			throw new ValidationException("User ID must be NULL");
		}
		return repository.save(user);
	}

	@Override
	public User update(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User must not be NULL");
		}
		if (user.getId() == null) {
			throw new ValidationException("User ID must not be NULL");
		}
		return repository.save(user);
	}

	@Override
	public void deleteById(Integer userId) {
		if (userId == null) {
			throw new IllegalArgumentException("User ID must not be NULL");
		}
		repository.deleteById(userId);
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		if (pageable == null) {
			throw new IllegalArgumentException("Pageable must not be NULL");
		}
		return repository.findAll(pageable);
	}

}
