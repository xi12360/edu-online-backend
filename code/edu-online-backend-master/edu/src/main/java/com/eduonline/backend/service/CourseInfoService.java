package com.eduonline.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.course.CourseInfoUploadRequest;
import com.eduonline.backend.model.entity.CourseInfo;
import com.eduonline.backend.model.vo.course.CourseInfoListVO;
import com.eduonline.backend.model.vo.course.CourseInfoVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Acer
* @description 针对表【course_info(课程信息)】的数据库操作Service
* @createDate 2023-08-25 16:59:15
*/
public interface CourseInfoService extends IService<CourseInfo> {

    BaseResponse<CourseInfoListVO> listInfos(String courseId, HttpServletRequest request);

    BaseResponse<CourseInfoVO> queryById(String courseInfoId, HttpServletRequest request);

    BaseResponse<Integer> uploadCourseInfo(CourseInfoUploadRequest courseInfoUploadRequest, HttpServletRequest request);
}
