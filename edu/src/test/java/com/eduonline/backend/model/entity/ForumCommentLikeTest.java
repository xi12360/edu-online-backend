package com.eduonline.backend.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ForumCommentLikeTest {
    ForumCommentLike forumCommentLike = new ForumCommentLike();

    @Test
    void testEquals() {
        boolean result = forumCommentLike.equals("that");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testHashCode() {
        int result = forumCommentLike.hashCode();
        Assertions.assertEquals(0, result);
    }

    @Test
    void testToString() {
        String result = forumCommentLike.toString();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme