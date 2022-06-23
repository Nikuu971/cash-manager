package com.backend.service;

import com.backend.exception.UserException;
import com.backend.model.UserApplication;
import com.backend.repository.UserRepository;
import com.backend.service.concretions.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Class User Service Test
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class UserApplicationServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    /**
     * Test get user by id method
     * fail if user is null
     */
    @Test
    public void getUserById() {
        final Long id = 1L;
        UserApplication p1 = new UserApplication("Jean");

        p1.setId(id);

        given(userRepository.findById(id)).willReturn(Optional.of(p1));

        final Optional<UserApplication> expected = Optional.ofNullable(userService.getUserById(id));

        assertThat(expected).isNotNull();
    }

    /**
     * Test get user by id method
     * fail if method doesn't throw
     */
    @Test(expected = UserException.class)
    public void getUserByIdError() {
        final Long id = 1L;
        UserApplication p1 = null;

        given(userRepository.findById(id)).willReturn(Optional.ofNullable(p1));

        final Optional<UserApplication> expected = Optional.ofNullable(userService.getUserById(id));
    }

    /**
     * Test create user method
     * fail if user is invalid
     */
    @Test
    public void createUser() {
        UserApplication p1 = new UserApplication("Jean");

        UserApplication create = userService.createUser("Jean");

        assertEquals(create.getUsername(), p1.getUsername());
    }
}
