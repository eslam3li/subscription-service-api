package ua.ivan909020.api.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import ua.ivan909020.api.annotations.ApiPageable;
import ua.ivan909020.api.domain.dao.User;
import ua.ivan909020.api.domain.dto.UserDto;
import ua.ivan909020.api.exceptions.EntityNotFoundException;
import ua.ivan909020.api.mappers.UserMapper;
import ua.ivan909020.api.services.UserService;

import javax.validation.Valid;
import java.util.List;

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
	@ApiPageable
	public List<UserDto> findAll(@ApiIgnore @PageableDefault(sort = "id") Pageable pageable) {
		return userMapper.toDto(userService.findAll(pageable).getContent());
	}

}
