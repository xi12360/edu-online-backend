package com.eduonline.backend.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AdminTest {
    Admin admin = new Admin();

    @Test
    void testEquals() {
        boolean result = admin.equals("that");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testHashCode() {
        int result = admin.hashCode();
        Assertions.assertEquals(0, result);
    }

    @Test
    void testToString() {
        String result = admin.toString();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme