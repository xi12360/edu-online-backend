package com.eduonline.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.entity.CourseFavor;
import com.eduonline.backend.model.vo.course.CourseListVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Acer
* @description 针对表【course_favor(课程收藏表)】的数据库操作Service
* @createDate 2023-08-25 16:59:15
*/
public interface CourseFavorService extends IService<CourseFavor> {

    BaseResponse<Integer> buyCourse(String studentId, String courseId, HttpServletRequest request);

    BaseResponse<Integer> queryBuy(String studentId,String courseId, HttpServletRequest request);

    BaseResponse<CourseListVO> listMyFavor(Integer current, Integer pageSize, String userId, HttpServletRequest request);

}

