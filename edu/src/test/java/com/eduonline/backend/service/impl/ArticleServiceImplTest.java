package com.eduonline.backend.service.impl;

import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.mapper.ArticleMapper;
import com.eduonline.backend.mapper.StudentMapper;
import com.eduonline.backend.mapper.TeacherMapper;
import com.eduonline.backend.model.dto.forum.ArticlePublishRequest;
import com.eduonline.backend.model.entity.Article;
import com.eduonline.backend.model.vo.article.ArticleListVO;
import com.eduonline.backend.model.vo.article.ArticleVO;
import org.apache.ibatis.logging.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class ArticleServiceImplTest {
    @Mock
    StudentMapper studentMapper;
    @Mock
    TeacherMapper teacherMapper;
    @Mock
    RedisTemplate redisTemplate;
    @Mock
    ArticleMapper articleMapper;
    @Mock
    Log log;
    @Mock
    //M baseMapper;
    //Field entityClass of type Class - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    //Field mapperClass of type Class - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @InjectMocks
    ArticleServiceImpl articleServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPublish() {
//        when(studentMapper.selectById(any(Serializable.class))).thenReturn(new T());
//        when(teacherMapper.selectById(any(Serializable.class))).thenReturn(new T());
//        when(articleMapper.selectById(any(Serializable.class))).thenReturn(new T());

        BaseResponse<Integer> result = articleServiceImpl.publish(new ArticlePublishRequest(), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testDelArticle() {
        BaseResponse<Integer> result = articleServiceImpl.delArticle("articleId", "userId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testListMostFavorArticle() {
        when(articleMapper.queryTop10ArticlesByPraiseNum(anyInt())).thenReturn(Arrays.<ArticleVO>asList(new ArticleVO()));

        BaseResponse<ArticleListVO> result = articleServiceImpl.listMostFavorArticle(Integer.valueOf(0), null);
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testListMostClickArticle() {
        when(articleMapper.queryTop10ArticlesByClickNum(anyInt())).thenReturn(Arrays.<ArticleVO>asList(new ArticleVO()));

        BaseResponse<ArticleListVO> result = articleServiceImpl.listMostClickArticle(Integer.valueOf(0), null);
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testLikeArticle() {
        when(redisTemplate.opsForHash()).thenReturn(null);

        BaseResponse<Integer> result = articleServiceImpl.likeArticle("articleId", "userId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testQueryArticleLike() {
        when(articleMapper.queryArticles(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(Arrays.<ArticleVO>asList(new ArticleVO()));
        when(articleMapper.queryTotal(anyString(), anyInt())).thenReturn(Long.valueOf(1));

        BaseResponse<ArticleListVO> result = articleServiceImpl.queryArticleLike("name", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testQueryArticleByAuthorName() {
        when(articleMapper.queryArticleByAuthorName(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(Arrays.<ArticleVO>asList(new ArticleVO()));
        when(articleMapper.queryTotal(anyString(), anyInt())).thenReturn(Long.valueOf(1));

        BaseResponse<ArticleListVO> result = articleServiceImpl.queryArticleByAuthorName("name", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testOnclickArticle() {
        when(redisTemplate.opsForHash()).thenReturn(null);

        BaseResponse<Integer> result = articleServiceImpl.onclickArticle("articleId", "studentId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testQueryById() {
        when(articleMapper.queryById(anyString())).thenReturn(new ArticleVO());

        BaseResponse<ArticleVO> result = articleServiceImpl.queryById("articleId", null);
        Assertions.assertEquals(new BaseResponse<ArticleVO>(0, new ArticleVO(), "message"), result);
    }

    @Test
    void testListMyPublish() {
        BaseResponse<ArticleListVO> result = articleServiceImpl.listMyPublish(Integer.valueOf(0), Integer.valueOf(0), "userId", null);
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testQueryByType() {
        when(articleMapper.queryArticleByType(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(Arrays.<ArticleVO>asList(new ArticleVO()));
        when(articleMapper.queryTotal(anyString(), anyInt())).thenReturn(Long.valueOf(1));

        BaseResponse<ArticleListVO> result = articleServiceImpl.queryByType("type", Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(0));
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testQueryByAuthorId() {
        BaseResponse<ArticleListVO> result = articleServiceImpl.queryByAuthorId("authorId", Integer.valueOf(0), Integer.valueOf(0), null);
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testQueryByTime() {
        when(articleMapper.queryTop10ArticlesByTime(anyInt(), anyInt(), anyInt())).thenReturn(Arrays.<ArticleVO>asList(new ArticleVO()));
        when(articleMapper.queryTotal()).thenReturn(Long.valueOf(1));

        BaseResponse<ArticleListVO> result = articleServiceImpl.queryByTime(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(0));
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testGetLikedKey() {
        String result = ArticleServiceImpl.getLikedKey("userId", "commentId");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetClickedKey() {
        String result = ArticleServiceImpl.getClickedKey("userId", "commentId");
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testGetBaseMapper() {
        ArticleMapper result = articleServiceImpl.getBaseMapper();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetEntityClass() {
        Class<Article> result = articleServiceImpl.getEntityClass();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testSaveBatch() {
        boolean result = articleServiceImpl.saveBatch(Arrays.<Article>asList(new Article()), 0);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testSaveOrUpdate() {
        boolean result = articleServiceImpl.saveOrUpdate(new Article());
        Assertions.assertEquals(true, result);
    }

    @Test
    void testSaveOrUpdateBatch() {
        boolean result = articleServiceImpl.saveOrUpdateBatch(Arrays.<Article>asList(new Article()), 0);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testUpdateBatchById() {
        boolean result = articleServiceImpl.updateBatchById(Arrays.<Article>asList(new Article()), 0);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testGetOne() {
        Article result = articleServiceImpl.getOne(null, true);
        Assertions.assertEquals(new Article(), result);
    }

    @Test
    void testGetMap() {
        Map<String, Object> result = articleServiceImpl.getMap(null);
        Assertions.assertEquals(new HashMap<String, Object>() {{
            put("replaceMeWithExpectedResult", "replaceMeWithExpectedResult");
        }}, result);
    }

    @Test
    void testGetObj() {
        Object result = articleServiceImpl.getObj(null, null);
        Assertions.assertEquals(new Object(), result);
    }

    @Test
    void testRemoveById() {
        boolean result = articleServiceImpl.removeById(null);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testRemoveByIds() {
        boolean result = articleServiceImpl.removeByIds(Arrays.asList(null));
        Assertions.assertEquals(true, result);
    }

    @Test
    void testRemoveById2() {
        boolean result = articleServiceImpl.removeById(null, true);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testRemoveBatchByIds() {
        boolean result = articleServiceImpl.removeBatchByIds(Arrays.asList(null), 0);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testRemoveBatchByIds2() {
        boolean result = articleServiceImpl.removeBatchByIds(Arrays.asList(null), 0, true);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testSave() {
        boolean result = articleServiceImpl.save(new Article());
        Assertions.assertEquals(true, result);
    }

    @Test
    void testCount() {
        long result = articleServiceImpl.count(null);
        Assertions.assertEquals(0L, result);
    }

}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme