package com.eduonline.backend.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CourseCommentLikeTest {
    CourseCommentLike courseCommentLike = new CourseCommentLike();

    @Test
    void testEquals() {
        boolean result = courseCommentLike.equals("that");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testHashCode() {
        int result = courseCommentLike.hashCode();
        Assertions.assertEquals(0, result);
    }

    @Test
    void testToString() {
        String result = courseCommentLike.toString();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme