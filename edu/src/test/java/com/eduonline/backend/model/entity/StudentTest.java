package com.eduonline.backend.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.mockito.Mockito.*;

class StudentTest {
    @Mock
    Date createTime;
    @InjectMocks
    Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEquals() {
        when(createTime.equals(any(Object.class))).thenReturn(true);

        boolean result = student.equals("that");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testHashCode() {
        when(createTime.hashCode()).thenReturn(0);

        int result = student.hashCode();
        Assertions.assertEquals(0, result);
    }

    @Test
    void testToString() {
        when(createTime.hashCode()).thenReturn(0);

        String result = student.toString();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme