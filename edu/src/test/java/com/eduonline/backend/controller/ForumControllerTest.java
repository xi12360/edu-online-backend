package com.eduonline.backend.controller;

import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.forum.ArticlePublishRequest;
import com.eduonline.backend.model.dto.forum.ForumCommentRequest;
import com.eduonline.backend.model.dto.forum.ReplyForumCommentRequest;
import com.eduonline.backend.model.vo.article.ArticleListVO;
import com.eduonline.backend.model.vo.article.ArticleVO;
import com.eduonline.backend.model.vo.forumComment.ForumCommentListVO;
import com.eduonline.backend.service.ArticleFavoritesService;
import com.eduonline.backend.service.ArticleService;
import com.eduonline.backend.service.ForumCommentService;
import com.eduonline.backend.service.MsgSystemService;
import com.eduonline.backend.service.impl.ArticleFavoritesServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

class ForumControllerTest {
    @Mock
    ArticleService articleService;
    @Mock
    ArticleFavoritesService articleFavoritesService;
    @Mock
    ForumCommentService commentService;
    @Mock
    ArticleFavoritesServiceImpl articleFavoritesServiceImpl;
    @Mock
    ForumCommentService forumCommentService;
    @Mock
    MsgSystemService msgSystemService;
    @InjectMocks
    ForumController forumController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPublish() {
        when(articleService.publish(any(ArticlePublishRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = forumController.publish(new ArticlePublishRequest(), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testQueryComment() {
        when(commentService.queryComment(anyString(), anyString(), anyInt(), anyInt(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<ForumCommentListVO>(0, new ForumCommentListVO(), "message"));
        when(forumCommentService.queryComment(anyString(), anyString(), anyInt(), anyInt(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<ForumCommentListVO>(0, new ForumCommentListVO(), "message"));

        BaseResponse<ForumCommentListVO> result = forumController.queryComment("articleId", "userId", Integer.valueOf(0), Integer.valueOf(0), null);
        Assertions.assertEquals(new BaseResponse<ForumCommentListVO>(0, new ForumCommentListVO(), "message"), result);
    }

    @Test
    void testComment() {
        when(commentService.comment(any(ForumCommentRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));
        when(forumCommentService.comment(any(ForumCommentRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = forumController.comment(new ForumCommentRequest(), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testReplyComment() {
        when(commentService.replyComment(any(ReplyForumCommentRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));
        when(forumCommentService.replyComment(any(ReplyForumCommentRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = forumController.replyComment(new ReplyForumCommentRequest(), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testDelComment() {
        when(commentService.delComment(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));
        when(forumCommentService.delComment(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = forumController.delComment("commentId", "userId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testDelArticle() {
        when(articleService.delArticle(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = forumController.delArticle("articleId", "userId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testListMostFavorArticle() {
        when(articleService.listMostFavorArticle(anyInt(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"));

        BaseResponse<ArticleListVO> result = forumController.listMostFavorArticle(Integer.valueOf(0), null);
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testListMostClickArticle() {
        when(articleService.listMostClickArticle(anyInt(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"));

        BaseResponse<ArticleListVO> result = forumController.listMostClickArticle(Integer.valueOf(0), null);
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testLikeComment() {
        when(commentService.likeComment(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));
        when(forumCommentService.likeComment(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = forumController.likeComment("commentId", "userId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testLikeArticle() {
        when(articleService.likeArticle(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = forumController.likeArticle("articleId", "userId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testFavorArticle() {
        when(articleFavoritesService.favorArticle(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));
        when(articleFavoritesServiceImpl.favorArticle(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = forumController.favorArticle("articleId", "userId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testQueryFavorArticle() {
        when(articleFavoritesService.queryFavorArticle(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));
        when(articleFavoritesServiceImpl.queryFavorArticle(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = forumController.queryFavorArticle("articleId", "userId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testQueryArticleLike() {
        when(articleService.queryArticleLike(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"));

        BaseResponse<ArticleListVO> result = forumController.queryArticleLike("name", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testQueryArticleByAuthorName() {
        when(articleService.queryArticleByAuthorName(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"));

        BaseResponse<ArticleListVO> result = forumController.queryArticleByAuthorName("name", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testOnclickArticle() {
        when(articleService.onclickArticle(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = forumController.onclickArticle("articleId", "studentId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testQueryByType() {
        when(articleService.queryByType(anyString(), anyInt(), anyInt(), any(HttpServletRequest.class), anyInt())).thenReturn(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"));

        BaseResponse<ArticleListVO> result = forumController.queryByType("type", Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(0));
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testQueryByAuthorId() {
        when(articleService.queryByAuthorId(anyString(), anyInt(), anyInt(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"));

        BaseResponse<ArticleListVO> result = forumController.queryByAuthorId("authorId", Integer.valueOf(0), Integer.valueOf(0), null);
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testQueryByTime() {
        when(articleService.queryByTime(anyInt(), anyInt(), any(HttpServletRequest.class), anyInt())).thenReturn(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"));

        BaseResponse<ArticleListVO> result = forumController.queryByTime(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(0));
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testQueryById() {
        when(articleService.queryById(anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<ArticleVO>(0, new ArticleVO(), "message"));

        BaseResponse<ArticleVO> result = forumController.queryById("articleId", null);
        Assertions.assertEquals(new BaseResponse<ArticleVO>(0, new ArticleVO(), "message"), result);
    }

    @Test
    void testListMyFavor() {
        when(articleFavoritesService.listMyFavor(anyInt(), anyInt(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"));
        when(articleFavoritesServiceImpl.listMyFavor(anyInt(), anyInt(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"));

        BaseResponse<ArticleListVO> result = forumController.listMyFavor(Integer.valueOf(0), Integer.valueOf(0), "userId", null);
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testListMyPublish() {
        when(articleService.listMyPublish(anyInt(), anyInt(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"));

        BaseResponse<ArticleListVO> result = forumController.listMyPublish(Integer.valueOf(0), Integer.valueOf(0), "userId", null);
        Assertions.assertEquals(new BaseResponse<ArticleListVO>(0, new ArticleListVO(), "message"), result);
    }

    @Test
    void testQueryChildComment() {
        when(commentService.queryChildComment(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<ForumCommentListVO>(0, new ForumCommentListVO(), "message"));
        when(forumCommentService.queryChildComment(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<ForumCommentListVO>(0, new ForumCommentListVO(), "message"));

        BaseResponse<ForumCommentListVO> result = forumController.queryChildComment("commentId", "userId", null);
        Assertions.assertEquals(new BaseResponse<ForumCommentListVO>(0, new ForumCommentListVO(), "message"), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme