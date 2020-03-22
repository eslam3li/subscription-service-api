package ua.ivan909020.api.controllers.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ua.ivan909020.api.domain.dao.User;
import ua.ivan909020.api.domain.dto.UserDto;
import ua.ivan909020.api.exceptions.EntityNotFoundException;
import ua.ivan909020.api.mappers.UserMapper;
import ua.ivan909020.api.services.UserService;

@RestController
@RequestMapping("/users")
public class UserRestController {

	private final UserService userService;
	private final UserMapper userMapper;

	@Autowired
	public UserRestController(UserService userService, UserMapper userMapper) {
		this.userMapper = userMapper;
		this.userService = userService;
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UserDto findById(@PathVariable Integer id) {
		User foundUser = userService.findById(id);
		if (foundUser == null) {
			throw new EntityNotFoundException("User with this id " + id + " not found");
		}
		return userMapper.toDto(foundUser);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto create(@Valid @RequestBody UserDto userDto) {
		User createdUser = userService.create(userMapper.toEntity(userDto));
		userDto.setId(createdUser.getId());
		return userDto;
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public UserDto update(@PathVariable Integer id, @Valid @RequestBody UserDto userDto) {
		if (!userService.existsById(id)) {
			throw new EntityNotFoundException("User with this id " + id + " not found");
		}
		userDto.setId(id);
		userService.update(userMapper.toEntity(userDto));
		return userDto;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void delete(@PathVariable Integer id) {
		if (!userService.existsById(id)) {
			throw new EntityNotFoundException("User with this id " + id + " not found");
		}
		userService.deleteById(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<UserDto> findAll(@RequestParam Integer page, @RequestParam Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		return userMapper.toDto(userService.findAll(pageable).getContent());
	}

}
