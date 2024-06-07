package com.eduonline.backend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ErrorCode;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.exception.BusinessException;
import com.eduonline.backend.mapper.CourseFavorMapper;
import com.eduonline.backend.mapper.CourseMapper;
import com.eduonline.backend.model.dto.course.CourseTestRequest;
import com.eduonline.backend.model.dto.course.CourseTestUploadRequest;
import com.eduonline.backend.model.entity.Course;
import com.eduonline.backend.model.entity.CourseFavor;
import com.eduonline.backend.model.entity.Student;
import com.eduonline.backend.model.vo.course.CourseTestListVO;
import com.eduonline.backend.model.vo.course.CourseTestVO;
import com.eduonline.backend.model.dto.course.SubmitTestRequest;
import com.eduonline.backend.service.CourseTestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.backend.model.entity.CourseTest;
import com.eduonline.backend.mapper.CourseTestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.eduonline.backend.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author Acer
* @description 针对表【course_test(测试题)】的数据库操作Service实现
* @createDate 2023-08-25 16:59:15
*/
@Service
public class CourseTestServiceImpl extends ServiceImpl<CourseTestMapper, CourseTest>
    implements CourseTestService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private CourseFavorMapper courseFavorMapper;

    /**
     * 上传测验题
     * @param courseTestUploadRequest
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> uploadTest(CourseTestUploadRequest courseTestUploadRequest, HttpServletRequest request) {
        String courseId = courseTestUploadRequest.getCourseId();
        List<CourseTestRequest> courseTestRequestList = courseTestUploadRequest.getCourseTestRequestList();
        if(courseTestRequestList == null || courseTestRequestList.isEmpty() || courseId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不能为空");
        }
        //校验课程
        Course course = courseMapper.selectById(courseId);
        if(course == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "课程id有误,课程不存在");
        }
        List<CourseTest> courseTestList;
        courseTestList = courseTestRequestList.stream().map(courseTestRequest -> {
            CourseTest courseTest = new CourseTest();
            BeanUtil.copyProperties(courseTestRequest, courseTest);
            courseTest.setId("course_test_" + RandomUtil.randomString(30));
            courseTest.setCourseId(courseId);
            return courseTest;
        }).collect(Collectors.toList());
        try {
            saveBatch(courseTestList);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "保存数据库失败");
        }

        return ResultUtils.success(1);
    }

    /**
     * 学生获取测验题
     *
     * @param courseId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CourseTestListVO> getTest(String courseId, HttpServletRequest request) {
        //获取测验题
        LambdaQueryWrapper<CourseTest> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTest::getCourseId, courseId);
        List<CourseTest> courseTestList = list(queryWrapper);
        List<CourseTestVO> courseTestVOList;
        courseTestVOList = courseTestList.stream().map(courseTest -> {
            CourseTestVO courseTestVO = new CourseTestVO();
            BeanUtil.copyProperties(courseTest, courseTestVO);
            return courseTestVO;
        }).collect(Collectors.toList());
        CourseTestListVO courseTestListVO = new CourseTestListVO();
        courseTestListVO.setCourseTestVOList(courseTestVOList);
        courseTestListVO.setTotalNum(courseTestListVO.getTotalNum());

        return ResultUtils.success(courseTestListVO);
    }
}




