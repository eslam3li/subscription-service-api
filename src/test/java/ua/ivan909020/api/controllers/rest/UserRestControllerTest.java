package ua.ivan909020.api.controllers.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.ivan909020.api.domain.dao.User;
import ua.ivan909020.api.domain.dto.UserDto;
import ua.ivan909020.api.mappers.UserMapper;
import ua.ivan909020.api.mappers.UserMapperImpl;
import ua.ivan909020.api.services.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.ivan909020.api.JsonMapper.toJson;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
public class UserRestControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private UserService userService;

    @MockBean(classes = UserMapperImpl.class)
    private UserMapper userMapper;

    @Test
    @WithMockUser(value = "username")
    public void findById() throws Exception {
        User user = createStubUser(1);
        UserDto userDto = convertToDto(user);

        when(userService.findById(1)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        mock.perform(
                get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(userDto)));

        verify(userService).findById(1);
        verify(userMapper).toDto(user);
    }

    @Test
    @WithMockUser(value = "username")
    public void findById_notFound() throws Exception {
        mock.perform(
                get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService).findById(1);
        verify(userMapper, times(0)).toDto(any(User.class));
    }

    @Test
    @WithMockUser(value = "username")
    public void create() throws Exception {
        User user = createStubUser(null);
        UserDto userDto = convertToDto(user);
        User createdUser = createStubUser(1);
        UserDto createdUserDto = convertToDto(createdUser);

        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userService.create(user)).thenReturn(createdUser);

        mock.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(toJson(createdUserDto)));

        verify(userMapper).toEntity(createdUserDto);
        verify(userService).create(user);
    }

    @Test
    @WithMockUser(value = "username")
    public void create_badRequest() throws Exception {
        User user = createStubUser(null);
        user.setName(null); // required parameter -> bad request
        UserDto userDto = convertToDto(user);

        mock.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userDto)))
                .andExpect(status().isBadRequest());

        verify(userMapper, times(0)).toEntity(userDto);
        verify(userService, times(0)).create(user);
    }

    @Test
    @WithMockUser(value = "username")
    public void update() throws Exception {
        User user = createStubUser(1);
        UserDto userDto = convertToDto(user);

        when(userService.existsById(1)).thenReturn(true);
        when(userMapper.toEntity(userDto)).thenReturn(user);

        mock.perform(
                put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(toJson(userDto)));

        verify(userService).existsById(1);
        verify(userMapper).toEntity(userDto);
        verify(userService).update(user);
    }

    @Test
    @WithMockUser(value = "username")
    public void update_notFound() throws Exception {
        User user = createStubUser(1);
        UserDto userDto = convertToDto(user);

        when(userService.existsById(1)).thenReturn(false);

        mock.perform(
                put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userDto)))
                .andExpect(status().isNotFound());

        verify(userService).existsById(1);
        verify(userMapper, times(0)).toEntity(userDto);
        verify(userService, times(0)).update(user);
    }

    @Test
    @WithMockUser(value = "username")
    public void update_badRequest() throws Exception {
        User user = createStubUser(null);
        user.setName(null); // required parameter -> bad request
        UserDto userDto = convertToDto(user);

        when(userService.existsById(1)).thenReturn(true);
        when(userMapper.toEntity(userDto)).thenReturn(user);

        mock.perform(
                put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userDto)))
                .andExpect(status().isBadRequest());

        verify(userService, times(0)).existsById(1);
        verify(userMapper, times(0)).toEntity(userDto);
        verify(userService, times(0)).update(user);
    }

    @Test
    @WithMockUser(value = "username")
    public void deleteById() throws Exception {
        when(userService.existsById(1)).thenReturn(true);

        mock.perform(
                delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        verify(userService).existsById(1);
        verify(userService).deleteById(1);
    }

    @Test
    @WithMockUser(value = "username")
    public void deleteById_notFound() throws Exception {
        when(userService.existsById(1)).thenReturn(false);

        mock.perform(
                delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService).existsById(1);
        verify(userService, times(0)).deleteById(1);
    }

    @Test
    @WithMockUser("username")
    public void findAll() throws Exception {
        List<User> users = Arrays.asList(createStubUser(1), createStubUser(2), createStubUser(3));
        List<UserDto> usersDto = convertToDto(users);

        when(userMapper.toDto(users)).thenReturn(usersDto);
        when(userService.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(users));

        mock.perform(
                get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(usersDto)));

        verify(userMapper).toDto(users);
        verify(userService).findAll(any(Pageable.class));
    }

    private User createStubUser(Integer id) {
        User user = new User();
        user.setId(id);
        user.setName("User");
        user.setBalance(10);
        return user;
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setBalance(user.getBalance());
        return userDto;
    }

    private List<UserDto> convertToDto(List<User> users) {
        List<UserDto> result = new ArrayList<>();
        for (User user : users) {
            result.add(convertToDto(user));
        }
        return result;
    }

}
