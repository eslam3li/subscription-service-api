package ua.ivan909020.api.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ua.ivan909020.api.domain.dao.User;
import ua.ivan909020.api.domain.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto toDto(User user);

	@Mapping(target = "subscriptions", ignore = true)
	User toEntity(UserDto userDto);

	List<UserDto> toDto(List<User> users);

}
