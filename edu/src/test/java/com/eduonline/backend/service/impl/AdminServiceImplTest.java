package com.eduonline.backend.service.impl;

import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.mapper.AdminMapper;
import com.eduonline.backend.model.dto.user.AdminLoginRequest;
import com.eduonline.backend.model.entity.Admin;
import org.apache.ibatis.logging.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

class AdminServiceImplTest {
    @Mock
    RedisTemplate redisTemplate;
    @Mock
    Log log;
    @Mock
    //M baseMapper;
    //Field entityClass of type Class - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    //Field mapperClass of type Class - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @InjectMocks
    AdminServiceImpl adminServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        when(redisTemplate.expire(any(Object.class), anyLong(), any(TimeUnit.class))).thenReturn(Boolean.TRUE);
        when(redisTemplate.opsForHash()).thenReturn(null);

        BaseResponse<String> result = adminServiceImpl.login(new AdminLoginRequest(), null);
        Assertions.assertEquals(new BaseResponse<String>(0, "data", "message"), result);
    }

    @Test
    void testLogout() {
        BaseResponse<Integer> result = adminServiceImpl.logout(null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testGetLoginAdmin() {
        Admin result = adminServiceImpl.getLoginAdmin(null);
        Assertions.assertEquals(new Admin(), result);
    }

    @Test
    void testGetBaseMapper() {
        AdminMapper result = adminServiceImpl.getBaseMapper();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetEntityClass() {
        Class<Admin> result = adminServiceImpl.getEntityClass();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testSaveBatch() {
        boolean result = adminServiceImpl.saveBatch(Arrays.<Admin>asList(new Admin()), 0);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testSaveOrUpdate() {
        boolean result = adminServiceImpl.saveOrUpdate(new Admin());
        Assertions.assertEquals(true, result);
    }

    @Test
    void testSaveOrUpdateBatch() {
        boolean result = adminServiceImpl.saveOrUpdateBatch(Arrays.<Admin>asList(new Admin()), 0);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testUpdateBatchById() {
        boolean result = adminServiceImpl.updateBatchById(Arrays.<Admin>asList(new Admin()), 0);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testGetOne() {
        Admin result = adminServiceImpl.getOne(null, true);
        Assertions.assertEquals(new Admin(), result);
    }

    @Test
    void testGetMap() {
        Map<String, Object> result = adminServiceImpl.getMap(null);
        Assertions.assertEquals(new HashMap<String, Object>() {{
            put("replaceMeWithExpectedResult", "replaceMeWithExpectedResult");
        }}, result);
    }

    @Test
    void testGetObj() {
        Object result = adminServiceImpl.getObj(null, null);
        Assertions.assertEquals(new Object(), result);
    }

    @Test
    void testRemoveById() {
        boolean result = adminServiceImpl.removeById(null);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testRemoveByIds() {
        boolean result = adminServiceImpl.removeByIds(Arrays.asList(null));
        Assertions.assertEquals(true, result);
    }

    @Test
    void testRemoveById2() {
        boolean result = adminServiceImpl.removeById(null, true);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testRemoveBatchByIds() {
        boolean result = adminServiceImpl.removeBatchByIds(Arrays.asList(null), 0);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testRemoveBatchByIds2() {
        boolean result = adminServiceImpl.removeBatchByIds(Arrays.asList(null), 0, true);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testGetById() {
        Admin result = adminServiceImpl.getById(null);
        Assertions.assertEquals(new Admin(), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme