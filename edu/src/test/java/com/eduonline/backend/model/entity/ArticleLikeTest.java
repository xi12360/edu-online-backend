package com.eduonline.backend.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArticleLikeTest {
    ArticleLike articleLike = new ArticleLike();

    @Test
    void testEquals() {
        boolean result = articleLike.equals("that");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testHashCode() {
        int result = articleLike.hashCode();
        Assertions.assertEquals(0, result);
    }

    @Test
    void testToString() {
        String result = articleLike.toString();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme