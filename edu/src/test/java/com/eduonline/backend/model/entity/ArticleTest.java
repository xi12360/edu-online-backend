package com.eduonline.backend.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.mockito.Mockito.*;

class ArticleTest {
    @Mock
    Date publishTime;
    @InjectMocks
    Article article;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEquals() {
        when(publishTime.equals(any(Object.class))).thenReturn(true);

        boolean result = article.equals("that");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testHashCode() {
        when(publishTime.hashCode()).thenReturn(0);

        int result = article.hashCode();
        Assertions.assertEquals(0, result);
    }

    @Test
    void testToString() {
        when(publishTime.hashCode()).thenReturn(0);

        String result = article.toString();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme