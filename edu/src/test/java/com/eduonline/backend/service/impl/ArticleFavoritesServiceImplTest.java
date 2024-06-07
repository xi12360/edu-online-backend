package com.eduonline.backend.service.impl;

import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.mapper.ArticleFavoritesMapper;
import com.eduonline.backend.mapper.ArticleMapper;
import com.eduonline.backend.model.entity.ArticleFavorites;
import com.eduonline.backend.model.vo.article.ArticleListVO;
import org.apache.ibatis.logging.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class ArticleFavoritesServiceImplTest<T> {
    @Mock
    ArticleMapper articleMapper;
    @Mock
    Log log;
    @Mock
    //M baseMapper;
    //Field entityClass of type Class - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    //Field mapperClass of type Class - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @InjectMocks
    ArticleFavoritesServiceImpl articleFavoritesServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFavorArticle() {
        BaseResponse<Integer> result = articleFavoritesServiceImpl.favorArticle("articleId", "userId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testQueryFavorArticle() {
        BaseResponse<Integer> result = articleFavoritesServiceImpl.queryFavorArticle("articleId", "userId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testListMyFavor() {
        //when(articleMapper.selectBatchIds(any(Collection.class))).thenReturn(Arrays.<T>asList(new T()));

        BaseResponse<ArticleListVO> result = articleFavoritesServiceImpl.listMyFavor(Integer.valueOf(0), Integer.valueOf(0), "userId", null);
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testGetBaseMapper() {
        ArticleFavoritesMapper result = articleFavoritesServiceImpl.getBaseMapper();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetEntityClass() {
        Class<ArticleFavorites> result = articleFavoritesServiceImpl.getEntityClass();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testSaveBatch() {
        boolean result = articleFavoritesServiceImpl.saveBatch(Arrays.<ArticleFavorites>asList(new ArticleFavorites()), 0);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testSaveOrUpdate() {
        boolean result = articleFavoritesServiceImpl.saveOrUpdate(new ArticleFavorites());
        Assertions.assertEquals(true, result);
    }

    @Test
    void testSaveOrUpdateBatch() {
        boolean result = articleFavoritesServiceImpl.saveOrUpdateBatch(Arrays.<ArticleFavorites>asList(new ArticleFavorites()), 0);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testUpdateBatchById() {
        boolean result = articleFavoritesServiceImpl.updateBatchById(Arrays.<ArticleFavorites>asList(new ArticleFavorites()), 0);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testGetOne() {
        ArticleFavorites result = articleFavoritesServiceImpl.getOne(null, true);
        Assertions.assertEquals(new ArticleFavorites(), result);
    }

    @Test
    void testGetMap() {
        Map<String, Object> result = articleFavoritesServiceImpl.getMap(null);
        Assertions.assertEquals(new HashMap<String, Object>() {{
            put("replaceMeWithExpectedResult", "replaceMeWithExpectedResult");
        }}, result);
    }

    @Test
    void testGetObj() {
        Object result = articleFavoritesServiceImpl.getObj(null, null);
        Assertions.assertEquals(new Object(), result);
    }

    @Test
    void testRemoveById() {
        boolean result = articleFavoritesServiceImpl.removeById(null);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testRemoveByIds() {
        boolean result = articleFavoritesServiceImpl.removeByIds(Arrays.asList(null));
        Assertions.assertEquals(true, result);
    }

    @Test
    void testRemoveById2() {
        boolean result = articleFavoritesServiceImpl.removeById(null, true);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testRemoveBatchByIds() {
        boolean result = articleFavoritesServiceImpl.removeBatchByIds(Arrays.asList(null), 0);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testRemoveBatchByIds2() {
        boolean result = articleFavoritesServiceImpl.removeBatchByIds(Arrays.asList(null), 0, true);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testSave() {
        boolean result = articleFavoritesServiceImpl.save(new ArticleFavorites());
        Assertions.assertEquals(true, result);
    }

    @Test
    void testCount() {
        long result = articleFavoritesServiceImpl.count(null);
        Assertions.assertEquals(0L, result);
    }

}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme