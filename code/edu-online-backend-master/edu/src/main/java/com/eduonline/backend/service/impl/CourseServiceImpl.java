package com.eduonline.backend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ErrorCode;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.exception.BusinessException;
import com.eduonline.backend.mapper.MsgSystemMapper;
import com.eduonline.backend.mapper.TeacherMapper;
import com.eduonline.backend.model.dto.course.CourseDropRequest;
import com.eduonline.backend.model.dto.course.CourseUploadRequest;
import com.eduonline.backend.model.entity.*;
import com.eduonline.backend.model.vo.article.ArticleClickListVO;
import com.eduonline.backend.model.vo.article.ArticleClickVO;
import com.eduonline.backend.model.vo.course.*;
import com.eduonline.backend.model.vo.article.ListAuditCourseVO;
import com.eduonline.backend.model.vo.user.TeacherVO;
import com.eduonline.backend.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.backend.mapper.CourseMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.eduonline.backend.constant.CommentConstant.*;
import static com.eduonline.backend.constant.UserConstant.*;

/**
 * @author Acer
 * @description 针对表【course(课程)】的数据库操作Service实现
 * @createDate 2023-08-25 16:59:15
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
        implements CourseService {

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private MsgSystemMapper msgSystemMapper;

    /**
     * 教师上传课程
     *
     * @param courseUploadRequest
     * @param request
     * @return
     */
    // todo 如要改为单点登录则改成redis校验
    @Override
    public BaseResponse<Integer> uploadCourse(CourseUploadRequest courseUploadRequest, HttpServletRequest request) {
        //校验教师id
        String teacherId = courseUploadRequest.getTeacherId();
        Teacher teacher = teacherMapper.selectById(teacherId);
        if (teacher == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "教师id不存在");
        }
        //保存课程
        Course course = new Course();
        BeanUtil.copyProperties(courseUploadRequest, course);
        course.setCourseId("course_" + RandomUtil.randomString(30));
        course.setStatus(0); // 0 为待审核状态
        course.setTeacherId(teacherId);
        course.setLessonNum("course_" + RandomUtil.randomString(10));
        save(course);
        return ResultUtils.success(1);
    }

    /**
     * 管理员审核课程
     *
     * @param courseId
     * @param status
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> auditCourse(String courseId, Integer status, HttpServletRequest request) {
        //审核课程
        LambdaUpdateWrapper<Course> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Course::getCourseId, courseId)
                .set(Course::getStatus, status);
        boolean isSuccess = update(wrapper);
        if (!isSuccess) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "数据库出错");
        }
        return ResultUtils.success(1);
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
    //List<CourseVO>
    @Override
    public BaseResponse<CourseListVO> queryCourseLike(String name, Integer current, Integer pageSize, Integer status) {
        //分页模糊查询
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        Page<Course> page = new Page<>(current, pageSize);
        wrapper.eq(Course::getStatus, status)
                .like(StringUtils.isNotBlank(name), Course::getTitle, name)
                .orderByDesc(Course::getUpdateTime);
        Page<Course> coursePage = page(page, wrapper);
        List<Course> courseList = coursePage.getRecords();
        long total = coursePage.getTotal();
        List<CourseVO> courseVOList;
        //对象拷贝
        courseVOList = courseList.stream().map(course -> {
            CourseVO courseVO = new CourseVO();
            BeanUtil.copyProperties(course, courseVO);
            //缓存获取实时点击数量
            Object obj = redisTemplate.opsForHash().get(COURSE_CLICK_COUNT, courseVO.getCourseId());
            int clickNum = 0;
            if(obj != null) {
                Integer num = (Integer) obj;
                if(num != null && num > 0) {
                    clickNum = num;
                }
            }
            courseVO.setClickNum(clickNum);
            return courseVO;
        }).collect(Collectors.toList());

        //封装对象并存总数
        CourseListVO courseListVO = new CourseListVO();
        courseListVO.setCourseVOList(courseVOList);
        courseListVO.setTotalNum(total);

        return ResultUtils.success(courseListVO);
    }

    /**
     * 管理员或教师下架课程
     *
     * @param courseDropRequest
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> dropCourse(CourseDropRequest courseDropRequest, HttpServletRequest request) {
        //下架课程
        String courseId = courseDropRequest.getCourseId();
        String message = courseDropRequest.getMessage();
        String teacherId = courseDropRequest.getTeacherId();
        LambdaUpdateWrapper<Course> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Course::getCourseId, courseId)
                .set(Course::getStatus, 2);
        boolean isSuccess = update(wrapper);
        if (!isSuccess) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "数据库出错");
        }
        //发送系统消息
        MsgSystem msgSystem = new MsgSystem();
        msgSystem.setContent("你的课程" + courseId + "下架原因:" + message);
        msgSystem.setId("sys_msg_" + RandomUtil.randomString(30));
        msgSystem.setToId(teacherId);
        int insert = msgSystemMapper.insert(msgSystem);
        if (insert == 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "数据库出错");
        }
        return ResultUtils.success(1);
    }

    /**
     * 管理员分页查询待审核课程
     *
     * @param current
     * @param pageSize
     * @param request
     * @return
     */
    @Override
    public BaseResponse<ListAuditCourseVO> listAudit(Integer current, Integer pageSize, HttpServletRequest request) {
        //分页条件查询 0为待审核
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getStatus, 0);

        Page<Course> page = new Page<>(current, pageSize);

        Page<Course> coursePage = page(page, wrapper);
        List<Course> courseList = coursePage.getRecords();
        long total = coursePage.getTotal();
        //对象属性复制
        List<CourseVO> courseVOList;
        courseVOList = courseList.stream().map(course -> {
            CourseVO courseVO = new CourseVO();
            BeanUtils.copyProperties(course, courseVO);
            return courseVO;
        }).collect(Collectors.toList());
        ListAuditCourseVO listAuditCourseVO = new ListAuditCourseVO();
        listAuditCourseVO.setAuditCourseVOList(courseVOList);
        listAuditCourseVO.setTotalNum(total);
        return ResultUtils.success(listAuditCourseVO);
    }

    /**
     * 根据id查找
     *
     * @param courseId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CourseVO> queryCourseById(String courseId, HttpServletRequest request) {
        //数据库查询
        Course course = getById(courseId);
        if (course == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "课程不存在");
        }
        String teacherId = course.getTeacherId();
        Teacher teacher = teacherMapper.selectById(teacherId);
        TeacherVO teacherVO = new TeacherVO();
        BeanUtil.copyProperties(teacher, teacherVO);

        //缓存获取实时点击数量
        Object obj = redisTemplate.opsForHash().get(COURSE_CLICK_COUNT, courseId);
        int clickNum = 0;
        if(obj != null) {
            Integer num = (Integer) obj;
            if(num != null && num > 0) {
                clickNum = num;
            }
        }

        CourseVO courseVO = new CourseVO();
        BeanUtil.copyProperties(course, courseVO);
        courseVO.setTeacherVO(teacherVO);
        courseVO.setClickNum(clickNum);
        return ResultUtils.success(courseVO);
    }

    /**
     * 点击课程
     * @param courseId
     * @param studentId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> onclickCourse(String courseId, String studentId, HttpServletRequest request) {
        // 用户id和评论id的相互联系 key为userId::commentId 值为0或1，代表点赞还是未点赞
        String key = getClickedKey(studentId, courseId);
        //判断当前登录用户是否点击过
        Object status = redisTemplate.opsForHash().get(COURSE_CLICK, key);
        //用户未点击
        if (status == null || (Integer) status == 0) { // 空值表示未记录点赞情况
            redisTemplate.opsForHash().put(COURSE_CLICK, key, 1);
            //点赞数+1
            redisTemplate.opsForHash().increment(COURSE_CLICK_COUNT, courseId, 1);
        }
        return ResultUtils.success(1);
    }

    /**
     * 列出我的课程
     * @param current
     * @param pageSize
     * @param userId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CourseListVO> listMyCourse(Integer current, Integer pageSize, String userId, HttpServletRequest request) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getTeacherId, userId);
        //筛选出该ID报名的课程
        Page<Course> page = new Page<>(current, pageSize);
        Page<Course> coursePage = page(page, wrapper);
        List<Course> courseList = coursePage.getRecords();
        long total = coursePage.getTotal();

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

    /**
     * 教师重新提交课程审核
     * @param courseId
     * @param status
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> reSubmitCourse(String courseId, Integer status, HttpServletRequest request) {
        LambdaUpdateWrapper<Course> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Course::getCourseId, courseId)
                .set(Course::getStatus, 0);
        boolean update = update(wrapper);
        if(!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "重新提交失败");
        }
        return ResultUtils.success(1);
    }

    @Override
    public BaseResponse<Integer> modifyCourse(CourseModifyRequest courseModifyRequest, HttpServletRequest request) {
        //校验教师id
        String teacherId = courseModifyRequest.getTeacherId();
        Teacher teacher = teacherMapper.selectById(teacherId);
        if (teacher == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "教师id不存在");
        }
        //保存课程
        Course course = new Course();
        BeanUtil.copyProperties(courseModifyRequest, course);
        updateById(course);
        return ResultUtils.success(1);
    }

    @Override
    public BaseResponse<CourseFavorListVO> listMostFavorCourse(HttpServletRequest request) {
        List<CourseMostFavorVO> courseFavorVOList = new ArrayList<>(); // 初始化一个空的列表，用于存放点赞量前十的文章ID

        // 从数据库中降序查询文章的点赞量信息，得到一个 List<ArticleLikes> 类型的列表
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Course::getBuyCount)
                .last("limit 10");
        List<Course> courseList = list(wrapper);

        // 从排序后的列表中获取前十篇文章的ID，并加入 mostFavorArticleIds 列表
        courseFavorVOList = courseList.stream().map(course -> {
            CourseMostFavorVO courseFavorVO = new CourseMostFavorVO();
            BeanUtils.copyProperties(course, courseFavorVO);
            courseFavorVO.setPraiseNum(course.getBuyCount());
            return courseFavorVO;
        }).collect(Collectors.toList());
        CourseFavorListVO courseFavorListVO = new CourseFavorListVO();
        courseFavorListVO.setTotalNum(0);
        if(courseFavorListVO != null && courseFavorVOList.size() > 0) {
            courseFavorListVO.setCourseFavorListVO(courseFavorVOList);
            courseFavorListVO.setTotalNum(courseFavorVOList.size());
        }

        return ResultUtils.success(courseFavorListVO);
    }

    @Override
    public BaseResponse<CourseClickListVO> listMostClickArticle(HttpServletRequest request) {
        List<CourseClickVO> courseClickVOList; // 初始化一个空的列表，用于存放点赞量前十的文章ID

        // 从数据库中降序查询文章的点赞量信息，得到一个 List<ArticleLikes> 类型的列表
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Course::getViewCount)
                .last("limit 10");
        List<Course> courseList = list(wrapper);

        // 从排序后的列表中获取前十篇文章的ID，并加入 mostFavorArticleIds 列表
        courseClickVOList = courseList.stream().map(course -> {
            CourseClickVO courseClickVO = new CourseClickVO();
            BeanUtils.copyProperties(course, courseClickVO);
            courseClickVO.setClickNum(course.getViewCount());
            return courseClickVO;
        }).collect(Collectors.toList());
        CourseClickListVO courseClickListVO = new CourseClickListVO();
        courseClickListVO.setTotalNum(0);
        if(courseClickVOList != null && courseClickVOList.size() > 0) {
            courseClickListVO.setCourseClickListVO(courseClickVOList);
            courseClickListVO.setTotalNum(courseClickVOList.size());
        }
        return ResultUtils.success(courseClickListVO);
    }

    /**
     * 校验管理员身份
     *
     * @param request
     */
    private void verifyAdmin(HttpServletRequest request) {
        //管理员校验
        Object obj = request.getSession().getAttribute(USER_LOGIN_STATE);
        Object roleObj = request.getSession().getAttribute(ADMIN_ROLE);
        Admin admin;
        Integer role;
        try {
            admin = (Admin) obj;
            role = (Integer) roleObj;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "你不是管理员,没有权限查看");
        }
    }

    static String getClickedKey(String userId, String commentId) {
        StringBuilder builder = new StringBuilder();
        builder.append(userId);
        builder.append(":clicked:");
        builder.append(commentId);
        return builder.toString();
    }

    static String getLikedKey(String userId, String commentId) {
        StringBuilder builder = new StringBuilder();
        builder.append(userId);
        builder.append(":liked:");
        builder.append(commentId);
        return builder.toString();
    }
}




