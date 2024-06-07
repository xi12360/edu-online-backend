package com.eduonline.backend.timer;

import com.eduonline.backend.mapper.ArticleLikeMapper;
import com.eduonline.backend.mapper.CourseCommentLikeMapper;
import com.eduonline.backend.mapper.ForumCommentLikeMapper;
import com.eduonline.backend.model.entity.*;
import com.eduonline.backend.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;

import static org.mockito.Mockito.*;

class RedisToMysqlSyncTaskTest {
    @Mock
    RedisTemplate redisTemplate;
    @Mock
    ArticleService articleService;
    @Mock
    ArticleLikeService articleLikeService;
    @Mock
    ForumCommentService forumCommentService;
    @Mock
    ForumCommentLikeService forumCommentLikeService;
    @Mock
    CourseCommentLikeService courseCommentLikeService;
    @Mock
    CourseCommentService courseCommentService;
    @Mock
    ArticleLikeMapper articleLikeMapper;
    @Mock
    ForumCommentLikeMapper forumCommentLikeMapper;
    @Mock
    CourseCommentLikeMapper courseCommentLikeMapper;
    @Mock
    CourseService courseService;
    @InjectMocks
    RedisToMysqlSyncTask redisToMysqlSyncTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSyncData() {
        when(redisTemplate.opsForHash()).thenReturn(null);
        when(articleService.saveBatch(any(Collection.class))).thenReturn(true);
        when(articleService.saveOrUpdateBatch(any(Collection.class))).thenReturn(true);
        when(articleService.updateBatchById(any(Collection.class))).thenReturn(true);
        when(articleLikeService.saveBatch(any(Collection.class))).thenReturn(true);
        when(articleLikeService.saveOrUpdateBatch(any(Collection.class))).thenReturn(true);
        when(articleLikeService.updateBatchById(any(Collection.class))).thenReturn(true);
        when(forumCommentService.saveBatch(any(Collection.class))).thenReturn(true);
        when(forumCommentService.saveOrUpdateBatch(any(Collection.class))).thenReturn(true);
        when(forumCommentService.updateBatchById(any(Collection.class))).thenReturn(true);
        when(forumCommentLikeService.saveBatch(any(Collection.class))).thenReturn(true);
        when(forumCommentLikeService.saveOrUpdateBatch(any(Collection.class))).thenReturn(true);
        when(forumCommentLikeService.updateBatchById(any(Collection.class))).thenReturn(true);
        when(courseCommentLikeService.saveBatch(any(Collection.class))).thenReturn(true);
        when(courseCommentLikeService.saveOrUpdateBatch(any(Collection.class))).thenReturn(true);
        when(courseCommentLikeService.updateBatchById(any(Collection.class))).thenReturn(true);
        when(courseCommentService.saveBatch(any(Collection.class))).thenReturn(true);
        when(courseCommentService.saveOrUpdateBatch(any(Collection.class))).thenReturn(true);
        when(courseCommentService.updateBatchById(any(Collection.class))).thenReturn(true);
        when(articleLikeMapper.countByCommentIdAndUserId(anyString(), anyString())).thenReturn(new ArticleLike());
        //when(articleLikeMapper.updateById(any(T.class))).thenReturn(0);
        when(forumCommentLikeMapper.countByCommentIdAndUserId(anyString(), anyString())).thenReturn(new ForumCommentLike());
        //when(forumCommentLikeMapper.updateById(any(T.class))).thenReturn(0);
        when(courseCommentLikeMapper.countByCommentIdAndUserId(anyString(), anyString())).thenReturn(new CourseCommentLike());
        //when(courseCommentLikeMapper.updateById(any(T.class))).thenReturn(0);
        when(courseService.saveBatch(any(Collection.class))).thenReturn(true);
        when(courseService.saveOrUpdateBatch(any(Collection.class))).thenReturn(true);
        when(courseService.updateBatchById(any(Collection.class))).thenReturn(true);

        redisToMysqlSyncTask.syncData();
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme