package com.bieliaiev.feedback_bot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bieliaiev.feedback_bot.cache.UserCache;
import com.bieliaiev.feedback_bot.model.BotUser;

class CacheServiceTest {

    private UserCache cache;
    private CacheService cacheService;

    @BeforeEach
    void setUp() {
        cache = mock(UserCache.class);
        cacheService = new CacheService(cache);
    }

    @Test
    void setChatId_shouldCreateNewUserIfNotExists() {
        Long chatId = 1L;
        when(cache.getUser(chatId)).thenReturn(null);

        cacheService.setChatId(chatId);

        verify(cache).putUser(argThat(user ->
                user.getChatId().equals(chatId) &&
                user.getPosition() == null &&
                user.getBranch() == null
        ));
    }

    @Test
    void setPosition_shouldUpdatePositionIfUserExists() {
        Long chatId = 1L;
        BotUser user = new BotUser(chatId, null, null);
        when(cache.getUser(chatId)).thenReturn(user);

        cacheService.setPosition(chatId, "Engineer");

        assertEquals("Engineer", user.getPosition(), "User position should be updated");
        verify(cache).putUser(user);
    }

    @Test
    void setBranch_shouldUpdateBranchIfPositionSet() {
        Long chatId = 1L;
        BotUser user = new BotUser(chatId, "Engineer", null);
        when(cache.getUser(chatId)).thenReturn(user);

        cacheService.setBranch(chatId, "Kyiv");

        assertEquals("Kyiv", user.getBranch(), "User branch should be updated only if position is set");
        verify(cache).putUser(user);
    }

    @Test
    void getUser_shouldReturnUserFromCache() {
        Long chatId = 1L;
        BotUser user = new BotUser(chatId, "Engineer", "Kyiv");
        when(cache.getUser(chatId)).thenReturn(user);

        BotUser result = cacheService.getUser(chatId);

        assertEquals(user, result, "getUser should return the user stored in cache");
    }

    @Test
    void isUserValid_shouldReturnTrueOnlyIfAllFieldsSet() {
        BotUser user = new BotUser(1L, "Engineer", "Kyiv");
        assertTrue(cacheService.isUserValid(user), "User should be valid if chatId, position, and branch are set");

        BotUser invalidUser = new BotUser(2L, null, "Kyiv");
        assertFalse(cacheService.isUserValid(invalidUser), "User should be invalid if position is null");
    }
}
