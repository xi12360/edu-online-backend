package com.eduonline.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.course.CourseCommentRequest;
import com.eduonline.backend.model.dto.course.ReplyCourseCommentRequest;
import com.eduonline.backend.model.entity.CourseComment;
import com.eduonline.backend.model.vo.course.CourseCommentListVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Acer
* @description 针对表【course_comment】的数据库操作Service
* @createDate 2023-08-25 16:59:15
*/
public interface CourseCommentService extends IService<CourseComment> {

    BaseResponse<CourseCommentListVO> queryComment(String courseId, String userId, Integer current, Integer pageSize, HttpServletRequest request);

    BaseResponse<Integer> comment(CourseCommentRequest courseCommentRequest, HttpServletRequest request);

    BaseResponse<Integer> replyComment(ReplyCourseCommentRequest replyCommentRequest, HttpServletRequest request);

    BaseResponse<Integer> delComment(String commentId, String userId, HttpServletRequest request);

    BaseResponse<Integer> likeComment(String commentId, String userId, HttpServletRequest request);

    BaseResponse<CourseCommentListVO> queryChildComment(String fatherCommentId, String userId, HttpServletRequest request);
}
