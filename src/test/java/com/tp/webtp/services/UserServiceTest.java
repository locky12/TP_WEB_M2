package com.tp.webtp.services;

import com.tp.webtp.dao.UserDAO;
import com.tp.webtp.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserDAO userDao;

    @InjectMocks
    UserService userService;

    @Test
    void cannotGetUserWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserById(null);
        });
        verify(userDao, never()).findById(any());
    }

    @Test
    void cannotSaveUserWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(null);
        });
        verify(userDao, never()).findById(any());
    }

    @Test
    void cannotDeleteUserWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(null);
        });
        verify(userDao, never()).findById(any());
    }

    @Test
    void cannotLoadUserWithNullUsername() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.loadUserByUsername(null);
        });
        verify(userDao, never()).findById(any());
    }

    @Test
    void cannotLoadUserWithBlankUsername() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.loadUserByUsername("     ");
        });
        verify(userDao, never()).findById(any());
    }

    @Test
    void cannotLoadUserWithEmptyUsername() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.loadUserByUsername("");
        });
        verify(userDao, never()).findById(any());
    }
}
