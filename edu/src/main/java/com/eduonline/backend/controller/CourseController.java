package com.eduonline.backend.controller;

import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.course.*;
import com.eduonline.backend.model.vo.article.ArticleClickListVO;
import com.eduonline.backend.model.vo.article.ArticleFavorListVO;
import com.eduonline.backend.model.vo.article.ListAuditCourseVO;
import com.eduonline.backend.model.vo.course.*;
import com.eduonline.backend.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author:  XDDYu
 * @time: 2023/8/28 15:41
 */
@RestController
@RequestMapping("/course")
@Slf4j
@CrossOrigin
public class CourseController {

    @Resource
    private CourseService courseService;

    @Resource
    private CourseCommentService commentService;

    @Resource
    private CourseTestService testService;

    @Resource
    private CourseFavorService courseFavorService;

    @Resource
    private CourseCommentService courseCommentService;

    @Resource
    private CourseInfoService courseInfoService;

    /**
     * 教师上传课程信息
     *
     * @param courseUploadRequest
     * @param request
     * @return
     */
    @PostMapping("/upload")
    public BaseResponse<Integer> uploadCourse(@RequestBody CourseUploadRequest courseUploadRequest, HttpServletRequest request) {
        return courseService.uploadCourse(courseUploadRequest, request);
    }

    /**
     * 教师修改课程信息
     *
     * @param courseModifyRequest
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public BaseResponse<Integer> modifyCourse(@RequestBody CourseModifyRequest courseModifyRequest, HttpServletRequest request) {
        return courseService.modifyCourse(courseModifyRequest, request);
    }


    /**
     * 分页查询待审核课程
     *
     * @param current
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/listAudit")
    //List<CourseVO>
    public BaseResponse<ListAuditCourseVO> listAudit(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest request) {
        return courseService.listAudit(current, pageSize, request);
    }

    /**
     * 管理员审核课程
     *
     * @param courseId
     * @param status
     * @param request
     * @return
     */
    @GetMapping("/audit")
    public BaseResponse<Integer> auditCourse(@RequestParam("courseId") String courseId,
                                             @RequestParam("status") Integer status,
                                             HttpServletRequest request) {
        return courseService.auditCourse(courseId, status, request);
    }

    /**
     * 教师重新提交待审核课程
     *
     * @param courseId
     * @param status
     * @param request
     * @return
     */
    @GetMapping("/reSubmit")
    public BaseResponse<Integer> reSubmitCourse(@RequestParam("courseId") String courseId,
                                             @RequestParam("status") Integer status,
                                             HttpServletRequest request) {
        return courseService.reSubmitCourse(courseId, status, request);
    }

    /**
     * 管理员或教师下架课程
     *
     * @param courseDropRequest
     * @param request
     * @return
     */
    @PostMapping("/drop")
    public BaseResponse<Integer> dropCourse(@RequestBody CourseDropRequest courseDropRequest,
                                            HttpServletRequest request) {
        return courseService.dropCourse(courseDropRequest, request);
    }

    /**
     * 根据名字分页模糊查询查找
     *
     * @param name
     * @param current
     * @param pageSize
     * @param status
     * @return
     */
    @GetMapping("/query")
    public BaseResponse<CourseListVO> queryCourseLike
    (@RequestParam(value = "name", defaultValue = "") String name,
     @RequestParam(value = "current", defaultValue = "1") Integer current,
     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
     @RequestParam(value = "status", defaultValue = "1") Integer status) {
        return courseService.queryCourseLike(name, current, pageSize, status);
    }

    /**
     * 根据id查找课程信息
     *
     * @param courseId
     * @return
     */
    @GetMapping("/queryById")
    public BaseResponse<CourseVO> queryCourseById
    (@RequestParam("courseId") String courseId,
     HttpServletRequest request) {
        return courseService.queryCourseById(courseId, request);
    }

    /**
     * 点击课程
     *
     * @param courseId
     * @param request
     * @return
     */
    @GetMapping("/onclick")
    public BaseResponse<Integer> onclickCourse(@RequestParam("courseId") String courseId,
                                               @RequestParam("studentId") String studentId,
                                               HttpServletRequest request) {
        return courseService.onclickCourse(courseId, studentId, request);
    }

    /**
     * 购买课程
     *
     * @return
     */
    @GetMapping("/buy")
    public BaseResponse<Integer> buyCourse(@RequestParam("studentId") String studentId,
                                           @RequestParam("courseId") String courseId,
                                           HttpServletRequest request) {
        return courseFavorService.buyCourse(studentId, courseId, request);
    }

    /**
     * 查询购买课程
     *
     * @return
     */
    @GetMapping("/queryBuy")
    public BaseResponse<Integer> queryBuyCourse(@RequestParam("studentId") String studentId,
                                                @RequestParam("courseId") String courseId,
                                                HttpServletRequest request) {
        return courseFavorService.queryBuy(studentId, courseId, request);
    }

    /**
     * 分页查看课程评论
     *
     * @param courseId
     * @param current
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/queryComment")
    public BaseResponse<CourseCommentListVO> queryComment
    (@RequestParam(value = "courseId") String courseId,
     @RequestParam(value = "userId") String userId,
     @RequestParam(value = "current", defaultValue = "1") Integer current,
     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
     HttpServletRequest request) {
        return commentService.queryComment(courseId,userId, current, pageSize, request);
    }

    /**
     * 查看子评论
     * @param request
     * @return
     */
    @GetMapping("/queryChildComment")
    public BaseResponse<CourseCommentListVO> queryChildComment
            (@RequestParam(value = "commentId") String commentId,
             @RequestParam(value = "userId") String userId,
             HttpServletRequest request) {
        return commentService.queryChildComment(commentId,userId, request);
    }

    /**
     * 发布评论
     *
     * @param courseCommentRequest
     * @param request
     * @return
     */
    @PostMapping("/comment")
    public BaseResponse<Integer> comment(@RequestBody CourseCommentRequest courseCommentRequest
            , HttpServletRequest request) {
        return commentService.comment(courseCommentRequest, request);
    }

    /**
     * 回复评论
     *
     * @param replyCommentRequest
     * @param request
     * @return
     */
    @PostMapping("/reply")
    public BaseResponse<Integer> replyComment(@RequestBody ReplyCourseCommentRequest replyCommentRequest
            , HttpServletRequest request) {
        return commentService.replyComment(replyCommentRequest, request);
    }

    /**
     * 评论者或管理员删除评论
     *
     * @param commentId
     * @param userId
     * @param request
     * @return
     */
    @DeleteMapping("/del/comment")
    public BaseResponse<Integer> delComment(@RequestParam("commentId") String commentId,
                                            @RequestParam("userId") String userId, HttpServletRequest request) {
        return commentService.delComment(commentId, userId, request);
    }

    /**
     * 上传测验题
     *
     * @param courseTestUploadRequest
     * @param request
     * @return
     */
    @PostMapping("/upload/test")
    public BaseResponse<Integer> uploadTest(@RequestBody CourseTestUploadRequest courseTestUploadRequest
            , HttpServletRequest request) {
        return testService.uploadTest(courseTestUploadRequest, request);
    }

    /**
     * 学生获取测验题
     *
     * @param courseId
     * @param request
     * @return
     */
    @GetMapping("/get/test")
    public BaseResponse<CourseTestListVO> getTest(@RequestParam("courseId") String courseId
            , HttpServletRequest request) {
        return testService.getTest(courseId, request);
    }

    /**
     * 查看用户购买课程
     *
     * @param request
     * @return
     */
    //
    @GetMapping("/listMyFavor")
    public BaseResponse<CourseListVO> listMyFavor(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                  @RequestParam("userId") String userId,
                                                  @RequestParam(value = "role", defaultValue = "1" )Integer role,
                                                  HttpServletRequest request) {
        if(role == 1) { //学生
            return courseFavorService.listMyFavor(current, pageSize, userId, request);
        } else {
            return courseService.listMyCourse(current, pageSize, userId, request);
        }
    }

    /**
     * 评论点赞
     *
     * @return
     */
    @GetMapping("/like/comment")
    public BaseResponse<Integer> likeComment(@RequestParam("commentId") String commentId,
                                             @RequestParam("userId") String userId,
                                             HttpServletRequest request) {
        return courseCommentService.likeComment(commentId, userId, request);
    }

    /**
     * 所有课程章节
     * @param courseId
     * @param request
     * @return
     */
    @GetMapping("/listAllInfo")
    public BaseResponse<CourseInfoListVO> listInfos(@RequestParam("courseId") String courseId,
                                                       HttpServletRequest request) {
        return courseInfoService.listInfos(courseId, request);
    }

    /**
     * 所有课程章节
     * @param courseInfoId
     * @param request
     * @return
     */
    @GetMapping("/info/queryById")
    public BaseResponse<CourseInfoVO> infoQueryById(@RequestParam("courseInfoId") String courseInfoId,
                                                    HttpServletRequest request) {
        return courseInfoService.queryById(courseInfoId, request);
    }

    /**
     * 教师上传课程信息
     *
     * @param courseInfoUploadRequest
     * @param request
     * @return
     */
    @PostMapping("/upload/courseInfo")
    public BaseResponse<Integer> uploadCourseInfo(@RequestBody CourseInfoUploadRequest courseInfoUploadRequest, HttpServletRequest request) {
        return courseInfoService.uploadCourseInfo(courseInfoUploadRequest, request);
    }

    /**
     * 用户查看点赞量前十的文章
     *
     * @param request
     * @return
     */
    @GetMapping("/mostFavorCourse")
    public BaseResponse<CourseFavorListVO> listMostFavorCourse(HttpServletRequest request) {
        return courseService.listMostFavorCourse(request);
    }

    /**
     * 用户查看点击数前十的文章
     *
     * @param request
     * @return
     */
    @GetMapping("/mostClickCourse")
    public BaseResponse<CourseClickListVO> listMostClickCourse(HttpServletRequest request) {
        return courseService.listMostClickArticle(request);
    }


}
