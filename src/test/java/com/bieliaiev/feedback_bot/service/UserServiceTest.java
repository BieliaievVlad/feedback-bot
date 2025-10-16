package com.bieliaiev.feedback_bot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bieliaiev.feedback_bot.entity.User;
import com.bieliaiev.feedback_bot.repository.UserRepository;

class UserServiceTest {

    private UserRepository mockRepository;
    private UserService service;

    @BeforeEach
    void setUp() {
        mockRepository = mock(UserRepository.class);
        service = new UserService(mockRepository);
    }

    @Test
    void loadUserByUsername_shouldReturnUser_whenFound() {
        User user = new User();
        user.setUsername("test");
        when(mockRepository.findByUsername("test")).thenReturn(user);

        User result = (User) service.loadUserByUsername("test");

        assertNotNull(result, "User should be returned when found");
        assertEquals("test", result.getUsername(), "Username should match");
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenNotFound() {
        when(mockRepository.findByUsername("missing")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                     () -> service.loadUserByUsername("missing"),
                     "Should throw UsernameNotFoundException when user not found");
    }
}
