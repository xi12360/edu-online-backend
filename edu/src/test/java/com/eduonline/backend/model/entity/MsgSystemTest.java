package com.eduonline.backend.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.mockito.Mockito.*;

class MsgSystemTest {
    @Mock
    Date addTime;
    @Mock
    Date updateTime;
    @InjectMocks
    MsgSystem msgSystem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEquals() {
        when(addTime.equals(any(Object.class))).thenReturn(true);
        when(updateTime.equals(any(Object.class))).thenReturn(true);

        boolean result = msgSystem.equals("that");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testHashCode() {
        when(addTime.hashCode()).thenReturn(0);
        when(updateTime.hashCode()).thenReturn(0);

        int result = msgSystem.hashCode();
        Assertions.assertEquals(0, result);
    }

    @Test
    void testToString() {
        when(addTime.hashCode()).thenReturn(0);
        when(updateTime.hashCode()).thenReturn(0);

        String result = msgSystem.toString();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme