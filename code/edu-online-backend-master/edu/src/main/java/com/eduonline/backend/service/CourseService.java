package com.eduonline.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.course.CourseDropRequest;
import com.eduonline.backend.model.dto.course.CourseUploadRequest;
import com.eduonline.backend.model.entity.Course;
import com.eduonline.backend.model.vo.course.*;
import com.eduonline.backend.model.vo.article.ArticleListVO;
import com.eduonline.backend.model.vo.course.CourseListVO;
import com.eduonline.backend.model.vo.course.CourseVO;
import com.eduonline.backend.model.vo.article.ListAuditCourseVO;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
* @author Acer
* @description 针对表【course(课程)】的数据库操作Service
* @createDate 2023-08-25 16:59:15
*/
public interface CourseService extends IService<Course> {

    BaseResponse<Integer> uploadCourse(CourseUploadRequest courseUploadRequest, HttpServletRequest request);

    BaseResponse<Integer> auditCourse(String courseId, Integer status, HttpServletRequest request);

    BaseResponse<CourseListVO> queryCourseLike(String name, Integer current, Integer pageSize, Integer status);

    BaseResponse<Integer> dropCourse(CourseDropRequest courseId, HttpServletRequest request);

    BaseResponse<ListAuditCourseVO> listAudit(Integer current, Integer pageSize, HttpServletRequest request);

    BaseResponse<CourseVO> queryCourseById(String courseId, HttpServletRequest request);

    BaseResponse<Integer> onclickCourse(String courseId, String studentId, HttpServletRequest request);

    BaseResponse<CourseListVO> listMyCourse(Integer current, Integer pageSize, String userId, HttpServletRequest request);

    BaseResponse<Integer> reSubmitCourse(String courseId, Integer status, HttpServletRequest request);

    BaseResponse<Integer> modifyCourse(CourseModifyRequest courseUploadRequest, HttpServletRequest request);

    BaseResponse<CourseFavorListVO> listMostFavorCourse(HttpServletRequest request);

    BaseResponse<CourseClickListVO> listMostClickArticle(HttpServletRequest request);
}

