package com.eduonline.backend.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ErrorCode;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.exception.BusinessException;
import com.eduonline.backend.mapper.CourseMapper;
import com.eduonline.backend.mapper.TeacherMapper;
import com.eduonline.backend.model.entity.Course;
import com.eduonline.backend.model.vo.course.CourseListVO;
import com.eduonline.backend.model.vo.course.CourseVO;
import com.eduonline.backend.service.CourseFavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.backend.model.entity.CourseFavor;
import com.eduonline.backend.mapper.CourseFavorMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Acer
 * @description 针对表【course_favor(课程收藏表)】的数据库操作Service实现
 * @createDate 2023-08-25 16:59:15
 */
@Service
public class CourseFavorServiceImpl extends ServiceImpl<CourseFavorMapper, CourseFavor>
        implements CourseFavorService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private TeacherMapper teacherMapper;

    /**
     * 课程购买
     *
     * @param studentId
     * @param request
     * @param courseId
     * @return
     */
    @Override
    public BaseResponse<Integer> buyCourse(String studentId, String courseId, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(studentId, courseId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        if (queryBuy(studentId, courseId, request).equals(ResultUtils.success(1))) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "本账号已购买该课程");
        }
        CourseFavor courseFavor = new CourseFavor();
        courseFavor.setCourseId(courseId);
        courseFavor.setId("course_favor_" + RandomUtil.randomString(30));
        courseFavor.setUserId(studentId);
        courseFavor.setAddTime(new Date());
        LambdaUpdateWrapper<Course> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Course::getCourseId, courseId)
                .setSql("buy_count = buy_count + 1");
        try {
            save(courseFavor);
            courseMapper.update(null, wrapper);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "数据库操作失败");
        }
        return ResultUtils.success(1);
    }


    /**
     * 查询课程是否购买
     *
     * @param studentId
     * @param courseId
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> queryBuy(String studentId, String courseId, HttpServletRequest request) {

        LambdaQueryWrapper<CourseFavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseFavor::getUserId, studentId);
        wrapper.eq(CourseFavor::getCourseId, courseId);
        long count = count(wrapper);
        if (count == 1) {
            return ResultUtils.success(1); // 如果已购买，则返回1
        } else {
            return ResultUtils.success(0); // 如果未购买，则返回0
        }

    }

    /**
     * 查出购买课程
     *
     * @param current
     * @param pageSize
     * @param userId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CourseListVO> listMyFavor(Integer current, Integer pageSize, String userId, HttpServletRequest request) {

            LambdaQueryWrapper<CourseFavor> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CourseFavor::getUserId, userId);
            //筛选出该ID报名的课程
            Page<CourseFavor> page = new Page<>(current, pageSize);
            Page<CourseFavor> coursePage = page(page, wrapper);
            List<CourseFavor> courseFavorsList = coursePage.getRecords();
            long total = coursePage.getTotal();

            List<String> courseIdList;
            courseIdList = courseFavorsList.stream().map(courseFavor -> {
                String courseId = courseFavor.getCourseId();
                return courseId;
            }).collect(Collectors.toList());
            List<Course> courseList = courseMapper.selectBatchIds(courseIdList);
            List<CourseVO> courseVOList;
            courseVOList = courseList.stream().map(course -> {
                CourseVO courseVO = new CourseVO();
                BeanUtils.copyProperties(course, courseVO);
                return courseVO;
            }).collect(Collectors.toList());

            CourseListVO courseListVO = new CourseListVO();
            courseListVO.setCourseVOList(courseVOList);
            courseListVO.setTotalNum(total);
            return ResultUtils.success(courseListVO);

    }




}





