package com.eduonline.backend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ErrorCode;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.exception.BusinessException;
import com.eduonline.backend.model.dto.course.CourseInfoUploadRequest;
import com.eduonline.backend.model.vo.course.CourseInfoListVO;
import com.eduonline.backend.model.vo.course.CourseInfoVO;
import com.eduonline.backend.service.CourseInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.backend.model.entity.CourseInfo;
import com.eduonline.backend.mapper.CourseInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Acer
* @description 针对表【course_info(课程信息)】的数据库操作Service实现
* @createDate 2023-08-25 16:59:15
*/
@Service
public class CourseInfoServiceImpl extends ServiceImpl<CourseInfoMapper, CourseInfo>
    implements CourseInfoService {

    /**
     * 查询所有章节
     * @param courseId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CourseInfoListVO> listInfos(String courseId, HttpServletRequest request) {
        if(courseId == null || courseId.equals("")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "id不能为空");
        }
        LambdaQueryWrapper<CourseInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseInfo::getCourseId, courseId)
                .orderByAsc(CourseInfo::getChapterNum);
        List<CourseInfo> courseInfoList = list(wrapper);
        List<CourseInfoVO> courseInfoVOList;
        courseInfoVOList = courseInfoList.stream().map(courseInfo -> {
            CourseInfoVO courseInfoVO = new CourseInfoVO();
            BeanUtil.copyProperties(courseInfo, courseInfoVO);
            return courseInfoVO;
        }).collect(Collectors.toList());
        int total = courseInfoVOList.size();
        CourseInfoListVO courseInfoListVO = new CourseInfoListVO();
        courseInfoListVO.setCourseInfoVOList(courseInfoVOList);
        courseInfoListVO.setTotalNum(total);
        return ResultUtils.success(courseInfoListVO);
    }

    /**
     * 根据id查询
     * @param courseInfoId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CourseInfoVO> queryById(String courseInfoId, HttpServletRequest request) {
        if(courseInfoId == null || courseInfoId.equals("")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "id不能为空");
        }
        CourseInfo courseInfo = getById(courseInfoId);
        if(courseInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "该章节不存在");
        }
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        BeanUtil.copyProperties(courseInfo, courseInfoVO);
        return ResultUtils.success(courseInfoVO);
    }

    /**
     * 更新
     * @param courseInfoUploadRequest
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> uploadCourseInfo(CourseInfoUploadRequest courseInfoUploadRequest, HttpServletRequest request) {
        if(courseInfoUploadRequest == null || StringUtils.isAnyBlank(courseInfoUploadRequest.getCourseId(),
                courseInfoUploadRequest.getContent())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不能为空");
        }
        String courseId = courseInfoUploadRequest.getCourseId();
        LambdaQueryWrapper<CourseInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseInfo::getCourseId, courseId);
        long count = count(wrapper);
        int chapterNum = (int) count + 1;
        CourseInfo courseInfo = new CourseInfo();
        BeanUtil.copyProperties(courseInfoUploadRequest, courseInfo);
        courseInfo.setChapterNum(chapterNum);
        save(courseInfo);
        return ResultUtils.success(1);
    }
}
