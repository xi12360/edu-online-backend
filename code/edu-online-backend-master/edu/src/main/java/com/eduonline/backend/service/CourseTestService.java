package com.eduonline.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.course.CourseTestUploadRequest;
import com.eduonline.backend.model.vo.course.CourseTestListVO;
import com.eduonline.backend.model.vo.course.CourseTestVO;
import com.eduonline.backend.model.dto.course.SubmitTestRequest;
import com.eduonline.backend.model.entity.CourseTest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Acer
* @description 针对表【course_test(测试题)】的数据库操作Service
* @createDate 2023-08-25 16:59:15
*/
public interface CourseTestService extends IService<CourseTest> {

    BaseResponse<Integer> uploadTest(CourseTestUploadRequest courseTestUploadRequest, HttpServletRequest request);

    BaseResponse<CourseTestListVO> getTest(String courseId, HttpServletRequest request);

}
