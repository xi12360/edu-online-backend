package com.eduonline.backend.controller;

import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.course.*;
import com.eduonline.backend.model.vo.article.ListAuditCourseVO;
import com.eduonline.backend.model.vo.course.*;
import com.eduonline.backend.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

class CourseControllerTest {
    @Mock
    CourseService courseService;
    @Mock
    CourseCommentService commentService;
    @Mock
    CourseTestService testService;
    @Mock
    CourseFavorService courseFavorService;
    @Mock
    CourseCommentService courseCommentService;
    @Mock
    CourseInfoService courseInfoService;
    @InjectMocks
    CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadCourse() {
        when(courseService.uploadCourse(any(CourseUploadRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = courseController.uploadCourse(new CourseUploadRequest(), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testModifyCourse() {
        when(courseService.modifyCourse(any(CourseModifyRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = courseController.modifyCourse(new CourseModifyRequest(), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testListAudit() {
        when(courseService.listAudit(anyInt(), anyInt(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<ListAuditCourseVO>(0, new ListAuditCourseVO(), "message"));

        BaseResponse<ListAuditCourseVO> result = courseController.listAudit(Integer.valueOf(0), Integer.valueOf(0), null);
        Assertions.assertEquals(new BaseResponse<ListAuditCourseVO>(0, new ListAuditCourseVO(), "message"), result);
    }

    @Test
    void testAuditCourse() {
        when(courseService.auditCourse(anyString(), anyInt(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = courseController.auditCourse("courseId", Integer.valueOf(0), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testReSubmitCourse() {
        when(courseService.reSubmitCourse(anyString(), anyInt(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = courseController.reSubmitCourse("courseId", Integer.valueOf(0), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testDropCourse() {
        when(courseService.dropCourse(any(CourseDropRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = courseController.dropCourse(new CourseDropRequest(), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testQueryCourseLike() {
        when(courseService.queryCourseLike(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(new BaseResponse<CourseListVO>(0, new CourseListVO(), "message"));

        BaseResponse<CourseListVO> result = courseController.queryCourseLike("name", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
        Assertions.assertEquals(new BaseResponse<CourseListVO>(0, new CourseListVO(), "message"), result);
    }

    @Test
    void testQueryCourseById() {
        when(courseService.queryCourseById(anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<CourseVO>(0, new CourseVO(), "message"));

        BaseResponse<CourseVO> result = courseController.queryCourseById("courseId", null);
        Assertions.assertEquals(new BaseResponse<CourseVO>(0, new CourseVO(), "message"), result);
    }

    @Test
    void testOnclickCourse() {
        when(courseService.onclickCourse(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = courseController.onclickCourse("courseId", "studentId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testBuyCourse() {
        when(courseFavorService.buyCourse(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = courseController.buyCourse("studentId", "courseId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testQueryBuyCourse() {
        when(courseFavorService.queryBuy(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = courseController.queryBuyCourse("studentId", "courseId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testQueryComment() {
        when(commentService.queryComment(anyString(), anyString(), anyInt(), anyInt(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<CourseCommentListVO>(0, new CourseCommentListVO(), "message"));
        when(courseCommentService.queryComment(anyString(), anyString(), anyInt(), anyInt(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<CourseCommentListVO>(0, new CourseCommentListVO(), "message"));

        BaseResponse<CourseCommentListVO> result = courseController.queryComment("courseId", "userId", Integer.valueOf(0), Integer.valueOf(0), null);
        Assertions.assertEquals(new BaseResponse<CourseCommentListVO>(0, new CourseCommentListVO(), "message"), result);
    }

    @Test
    void testQueryChildComment() {
        when(commentService.queryChildComment(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<CourseCommentListVO>(0, new CourseCommentListVO(), "message"));
        when(courseCommentService.queryChildComment(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<CourseCommentListVO>(0, new CourseCommentListVO(), "message"));

        BaseResponse<CourseCommentListVO> result = courseController.queryChildComment("commentId", "userId", null);
        Assertions.assertEquals(new BaseResponse<CourseCommentListVO>(0, new CourseCommentListVO(), "message"), result);
    }

    @Test
    void testComment() {
        when(commentService.comment(any(CourseCommentRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));
        when(courseCommentService.comment(any(CourseCommentRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = courseController.comment(new CourseCommentRequest(), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testReplyComment() {
        when(commentService.replyComment(any(ReplyCourseCommentRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));
        when(courseCommentService.replyComment(any(ReplyCourseCommentRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = courseController.replyComment(new ReplyCourseCommentRequest(), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testDelComment() {
        when(commentService.delComment(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));
        when(courseCommentService.delComment(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = courseController.delComment("commentId", "userId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testUploadTest() {
        when(testService.uploadTest(any(CourseTestUploadRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = courseController.uploadTest(new CourseTestUploadRequest(), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testGetTest() {
        when(testService.getTest(anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<CourseTestListVO>(0, new CourseTestListVO(), "message"));

        BaseResponse<CourseTestListVO> result = courseController.getTest("courseId", null);
        Assertions.assertEquals(new BaseResponse<CourseTestListVO>(0, new CourseTestListVO(), "message"), result);
    }

    @Test
    void testListMyFavor() {
        when(courseService.listMyCourse(anyInt(), anyInt(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<CourseListVO>(0, new CourseListVO(), "message"));
        when(courseFavorService.listMyFavor(anyInt(), anyInt(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<CourseListVO>(0, new CourseListVO(), "message"));

        BaseResponse<CourseListVO> result = courseController.listMyFavor(Integer.valueOf(0), Integer.valueOf(0), "userId", Integer.valueOf(0), null);
        Assertions.assertEquals(new BaseResponse<CourseListVO>(0, new CourseListVO(), "message"), result);
    }

    @Test
    void testLikeComment() {
        when(commentService.likeComment(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));
        when(courseCommentService.likeComment(anyString(), anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = courseController.likeComment("commentId", "userId", null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testListInfos() {
        when(courseInfoService.listInfos(anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<CourseInfoListVO>(0, new CourseInfoListVO(), "message"));

        BaseResponse<CourseInfoListVO> result = courseController.listInfos("courseId", null);
        Assertions.assertEquals(new BaseResponse<CourseInfoListVO>(0, new CourseInfoListVO(), "message"), result);
    }

    @Test
    void testInfoQueryById() {
        when(courseInfoService.queryById(anyString(), any(HttpServletRequest.class))).thenReturn(new BaseResponse<CourseInfoVO>(0, new CourseInfoVO(), "message"));

        BaseResponse<CourseInfoVO> result = courseController.infoQueryById("courseInfoId", null);
        Assertions.assertEquals(new BaseResponse<CourseInfoVO>(0, new CourseInfoVO(), "message"), result);
    }

    @Test
    void testUploadCourseInfo() {
        when(courseInfoService.uploadCourseInfo(any(CourseInfoUploadRequest.class), any(HttpServletRequest.class))).thenReturn(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"));

        BaseResponse<Integer> result = courseController.uploadCourseInfo(new CourseInfoUploadRequest(), null);
        Assertions.assertEquals(new BaseResponse<Integer>(0, Integer.valueOf(0), "message"), result);
    }

    @Test
    void testListMostFavorCourse() {
        when(courseService.listMostFavorCourse(any(HttpServletRequest.class))).thenReturn(new BaseResponse<CourseFavorListVO>(0, new CourseFavorListVO(), "message"));

        BaseResponse<CourseFavorListVO> result = courseController.listMostFavorCourse(null);
        Assertions.assertEquals(new BaseResponse<CourseFavorListVO>(0, new CourseFavorListVO(), "message"), result);
    }

    @Test
    void testListMostClickCourse() {
        when(courseService.listMostClickArticle(any(HttpServletRequest.class))).thenReturn(new BaseResponse<CourseClickListVO>(0, new CourseClickListVO(), "message"));

        BaseResponse<CourseClickListVO> result = courseController.listMostClickCourse(null);
        Assertions.assertEquals(new BaseResponse<CourseClickListVO>(0, new CourseClickListVO(), "message"), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme