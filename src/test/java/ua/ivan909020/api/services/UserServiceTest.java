package ua.ivan909020.api.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import ua.ivan909020.api.domain.dao.User;
import ua.ivan909020.api.exceptions.ValidationException;
import ua.ivan909020.api.services.impl.UserServiceImpl;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean(classes = UserServiceImpl.class)
    private UserService userService;

    @Test
    public void existsById() {
        Integer userId = 1;

        when(userService.existsById(userId)).thenReturn(true);

        boolean isUserExists = userService.existsById(userId);

        assertTrue(isUserExists);
    }

    @Test
    public void existsById_idIsNull_throwsException() {
        Integer userId = null;

        when(userService.existsById(userId)).thenCallRealMethod();

        try {
            userService.existsById(userId);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("User ID must not be NULL", e.getMessage());
        }
    }

    @Test
    public void findById() {
        User user = createStubUser(1);

        when(userService.findById(user.getId())).thenReturn(user);

        User foundUser = userService.findById(1);

        assertEquals(foundUser, user);
    }

    @Test
    public void findById_idIsNull_throwsException() {
        Integer userId = null;

        when(userService.findById(userId)).thenCallRealMethod();

        try {
            userService.findById(userId);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("User ID must not be NULL", e.getMessage());
        }
    }

    @Test
    public void create() {
        User userToCreate = createStubUser(null);

        when(userService.create(userToCreate)).thenAnswer(invocation -> {
            User user = cloneStubUser((User) invocation.getArguments()[0]);
            user.setId(1);
            return user;
        });

        User createdUser = userService.create(userToCreate);

        User expectedUser = createStubUser(createdUser.getId());

        assertNotNull(createdUser.getId());
        assertEquals(expectedUser, createdUser);
    }

    @Test
    public void create_null_throwsException() {
        User userToCreate = null;

        when(userService.create(userToCreate)).thenCallRealMethod();

        try {
            userService.create(userToCreate);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("User must not be NULL", e.getMessage());
        }
    }

    @Test
    public void create_withId_throwsException() {
        User userToCreate = createStubUser(1);

        when(userService.create(userToCreate)).thenCallRealMethod();

        try {
            userService.create(userToCreate);
            fail();
        } catch (ValidationException e) {
            assertEquals("User ID must be NULL", e.getMessage());
        }
    }

    @Test
    public void update() {
        User createdUser = createStubUser(1);

        when(userService.update(createdUser)).thenAnswer(invocation -> {
            User user = cloneStubUser((User) invocation.getArguments()[0]);
            user.setName("Updated User");
            return user;
        });

        createdUser.setName("Updated User");
        User updatedUser = userService.update(createdUser);

        User expectedUser = createStubUser(createdUser.getId());
        expectedUser.setName(createdUser.getName());

        assertEquals(expectedUser, updatedUser);
    }

    @Test
    public void update_null_throwsException() {
        User userToUpdate = null;

        when(userService.update(userToUpdate)).thenCallRealMethod();

        try {
            userService.update(userToUpdate);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("User must not be NULL", e.getMessage());
        }
    }

    @Test
    public void update_withoutId_throwsException() {
        User userToUpdate = createStubUser(null);

        when(userService.update(userToUpdate)).thenCallRealMethod();

        try {
            userService.update(userToUpdate);
            fail();
        } catch (ValidationException e) {
            assertEquals("User ID must not be NULL", e.getMessage());
        }
    }

    @Test
    public void deleteById() {
        Integer userId = 1;

        when(userService.findById(userId)).thenReturn(null);

        userService.deleteById(userId);
        User receivedUser = userService.findById(userId);

        assertNull(receivedUser);
    }

    @Test
    public void deleteById_idIsNull_throwsException() {
        Integer userId = null;

        doCallRealMethod().when(userService).deleteById(userId);

        try {
            userService.deleteById(userId);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("User ID must not be NULL", e.getMessage());
        }
    }

    @Test
    public void findAll() {
        Page<User> users = new PageImpl<>(Arrays.asList(createStubUser(1), createStubUser(2), createStubUser(3)));

        when(userService.findAll(any(Pageable.class))).thenReturn(users);

        Page<User> receivedUsers = userService.findAll(Pageable.unpaged());

        assertTrue(receivedUsers.toList().containsAll(users.toList()));
    }

    @Test
    public void findAll_pageableIsNull_throwsException() {
        Pageable pageable = null;

        when(userService.findAll(pageable)).thenCallRealMethod();

        try {
            userService.findAll(pageable);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Pageable must not be NULL", e.getMessage());
        }
    }

    private User createStubUser(Integer id) {
        User user = new User();
        user.setId(id);
        user.setName("User");
        user.setBalance(10);
        return user;
    }

    private User cloneStubUser(User user) {
        User clonedUser = new User();
        clonedUser.setId(user.getId());
        clonedUser.setName(user.getName());
        clonedUser.setBalance(user.getBalance());
        return clonedUser;
    }

}
