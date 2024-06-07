package com.eduonline.backend.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.Mockito.*;

class LoginRequiredFilterTest {
    @Mock
    StringRedisTemplate redisTemplate;
    @Mock
    AntPathMatcher PATH_MATCHER;
    @InjectMocks
    LoginRequiredFilter loginRequiredFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilter() throws ServletException, IOException {
        when(PATH_MATCHER.match(anyString(), anyString())).thenReturn(true);

        loginRequiredFilter.doFilter(null, null, null);
    }

    @Test
    void testCheck() {
        when(PATH_MATCHER.match(anyString(), anyString())).thenReturn(true);

        boolean result = loginRequiredFilter.check(new String[]{"urls"}, "requestURI");
        Assertions.assertEquals(true, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme