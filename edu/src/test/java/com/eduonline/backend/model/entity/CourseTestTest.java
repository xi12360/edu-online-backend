package com.eduonline.backend.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CourseTestTest {
    CourseTest courseTest = new CourseTest();

    @Test
    void testEquals() {
        boolean result = courseTest.equals("that");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testHashCode() {
        int result = courseTest.hashCode();
        Assertions.assertEquals(0, result);
    }

    @Test
    void testToString() {
        String result = courseTest.toString();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme