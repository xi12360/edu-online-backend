package com.eduonline.backend.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CourseInfoTest {
    CourseInfo courseInfo = new CourseInfo();

    @Test
    void testEquals() {
        boolean result = courseInfo.equals("that");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testHashCode() {
        int result = courseInfo.hashCode();
        Assertions.assertEquals(0, result);
    }

    @Test
    void testToString() {
        String result = courseInfo.toString();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme