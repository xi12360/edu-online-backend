package com.eduonline.backend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ErrorCode;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.exception.BusinessException;
import com.eduonline.backend.mapper.AdminMapper;
import com.eduonline.backend.model.dto.course.CourseCommentRequest;
import com.eduonline.backend.model.dto.course.ReplyCourseCommentRequest;
import com.eduonline.backend.model.entity.Admin;
import com.eduonline.backend.model.vo.course.CourseCommentListVO;
import com.eduonline.backend.model.vo.course.CourseCommentVO;
import com.eduonline.backend.service.CourseCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.backend.model.entity.CourseComment;
import com.eduonline.backend.mapper.CourseCommentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.eduonline.backend.constant.ArticleConstant.FORUM_COMMENT_LIKE;
import static com.eduonline.backend.constant.CommentConstant.*;
import static com.eduonline.backend.constant.UserConstant.ADMIN_ROLE;
import static com.eduonline.backend.service.impl.ArticleServiceImpl.getLikedKey;
import static com.eduonline.backend.service.impl.CourseServiceImpl.getClickedKey;

/**
* @author Acer
* @description 针对表【course_comment】的数据库操作Service实现
* @createDate 2023-08-25 16:59:15
*/
@Service
public class CourseCommentServiceImpl extends ServiceImpl<CourseCommentMapper, CourseComment>
    implements CourseCommentService {

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Resource
    private CourseCommentMapper courseCommentMapper;

    @Resource
    private AdminMapper adminMapper;

    /**
     * 分页查看课程评论
     *
     * @param courseId
     * @param userId
     * @param current
     * @param pageSize
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CourseCommentListVO> queryComment(String courseId, String userId, Integer current, Integer pageSize, HttpServletRequest request) {
        if(courseId == null || courseId.equals("")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "课程id不应为空");
        }
        List<CourseCommentVO> courseCommentVOList;
        Long total;
        try {
            courseCommentVOList = courseCommentMapper.queryComments(courseId, (current - 1) * pageSize, pageSize);
            total = courseCommentMapper.queryTotal(courseId);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "mapper操作出现问题");
        }
        courseCommentVOList = courseCommentVOList.stream().map(courseCommentVO -> {
            //查询点赞总数
            String commentId = courseCommentVO.getCommentId();
            Object countObj = redisTemplate.opsForHash().get(COURSE_COMMENT_LIKE_COUNT, commentId);
            if(countObj == null) {
                courseCommentVO.setLiked(false);
                return courseCommentVO;
            }
            Integer praiseCount = (Integer) countObj;
            if(praiseCount == null) {
                courseCommentVO.setLiked(false);
                return courseCommentVO;
            }
            courseCommentVO.setPraiseCount(praiseCount);

            //查询当前用户是否点赞
            String key = getLikedKey(userId, commentId);
            Object likedObj = redisTemplate.opsForHash().get(COURSE_COMMENT_LIKE, key);
            if(likedObj == null) {
                courseCommentVO.setLiked(false);
                return courseCommentVO;
            }
            Integer liked = (Integer) likedObj;
            if(liked == null || liked == 0) {
                courseCommentVO.setLiked(false);
                return courseCommentVO;
            }
            courseCommentVO.setLiked(true);
            return courseCommentVO;
        }).collect(Collectors.toList());
        CourseCommentListVO courseCommentListVO = new CourseCommentListVO();
        courseCommentListVO.setCourseCommentVOList(courseCommentVOList);
        courseCommentListVO.setTotalNum(total);
        return ResultUtils.success(courseCommentListVO);
    }

    /**
     * 课程评论
     * @param courseCommentRequest
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> comment(CourseCommentRequest courseCommentRequest, HttpServletRequest request) {
        if(courseCommentRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不能为空");
        }
        String courseId = courseCommentRequest.getCourseId();
        String content = courseCommentRequest.getContent();
        String userId = courseCommentRequest.getUserId();
        if(StringUtils.isAnyBlank(courseId, content, userId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不能为空");
        }
        //保存评论
        CourseComment courseComment = new CourseComment();
        courseComment.setCommentId("course_comment_" + RandomUtil.randomString(30));
        courseComment.setCourseId(courseId);
        courseComment.setUserId(userId);
        courseComment.setContent(content);
        courseComment.setAddTime(new Date());
        try {
            save(courseComment);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "操作数据库出现错误");
        }
        return ResultUtils.success(1);
    }

    /**
     * 回复评论
     * @param replyCommentRequest
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> replyComment(ReplyCourseCommentRequest replyCommentRequest, HttpServletRequest request) {
        if(replyCommentRequest == null ) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不能为空");
        }
        String courseId= replyCommentRequest.getCourseId();
        String comment = replyCommentRequest.getComment();
        String userId = replyCommentRequest.getUserId();
        String pCommentId = replyCommentRequest.getCommentId();
        String otherId = replyCommentRequest.getOtherId();
        if(StringUtils.isAnyBlank(courseId, comment, userId, pCommentId, otherId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不能为空");
        }
        //保存评论
        CourseComment courseComment = new CourseComment();
        BeanUtil.copyProperties(replyCommentRequest, courseComment);
        courseComment.setCommentId("course_comment_" + RandomUtil.randomString(30));
        courseComment.setPCommentId(pCommentId);
        courseComment.setCourseId(courseId);
        courseComment.setOtherId(otherId);
        courseComment.setContent(comment);
        courseComment.setAddTime(new Date());
        try {
            save(courseComment);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "操作数据库出现错误");
        }
        return ResultUtils.success(1);
    }

    /**
     * 删除评论
     * @param commentId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> delComment(String commentId, String userId, HttpServletRequest request) {
        if(commentId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "id不能为空");
        }
        // 查询数据库,校验删除者是否是本人或管理员
        LambdaQueryWrapper<CourseComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseComment::getCommentId, commentId)
                .eq(CourseComment::getUserId, userId);
        CourseComment courseComment = getOne(wrapper);
        if(courseComment == null) {
            //校验是否是管理员
            Admin admin = adminMapper.selectById(userId);
            if(admin == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "评论和所属者id不匹配或不存在");
            }
        }

        //校验成功删除数据
        try {
            super.removeById(commentId);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "删除数据出现异常");
        }
        return ResultUtils.success(1);
    }

    /**
     * 点赞评论
     * @param commentId
     * @param userId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> likeComment(String commentId, String userId, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(commentId, userId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        // 用户id和评论id的相互联系 key为userId::commentId 值为0或1，代表点赞还是未点赞
        String key = getLikedKey(userId, commentId);
        //判断当前登录用户是否点赞
        Object status = redisTemplate.opsForHash().get(COURSE_COMMENT_LIKE, key);
        //用户未点赞
        if(status == null || (Integer) status == 0) { // 空值表示未记录点赞情况
            redisTemplate.opsForHash().put(COURSE_COMMENT_LIKE, key, 1);
            //点赞数+1
            redisTemplate.opsForHash().increment(COURSE_COMMENT_LIKE_COUNT, commentId, 1);
        }
        //用户已经点赞
        else if((Integer) status == 1) {
            redisTemplate.opsForHash().put(COURSE_COMMENT_LIKE, key, 0);
            //点赞数-1
            redisTemplate.opsForHash().increment(COURSE_COMMENT_LIKE_COUNT, commentId, -1);
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "系统异常，点赞失败");
        }

        return ResultUtils.success(1);
    }

    /**
     * 分页查找子评论
     *
     * @param commentId
     * @param userId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<CourseCommentListVO> queryChildComment(String commentId, String userId, HttpServletRequest request) {
        if(commentId == null || commentId.equals("")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "id不应为空");
        }
        List<CourseCommentVO> courseCommentVOList;
        long total;
        try {
            courseCommentVOList = courseCommentMapper.queryChildComments(commentId);
            total = courseCommentVOList.size();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "mapper操作出现问题");
        }
        courseCommentVOList = courseCommentVOList.stream().map(courseCommentVO -> {
            //查询点赞总数
            String courseCommentId = courseCommentVO.getCommentId();
            Object countObj = redisTemplate.opsForHash().get(COURSE_COMMENT_LIKE_COUNT, courseCommentId);
            if(countObj == null) {
                courseCommentVO.setLiked(false);
                courseCommentVO.setPraiseCount(0);
                return courseCommentVO;
            }
            Integer praiseCount = (Integer) countObj;
            if(praiseCount == null) {
                courseCommentVO.setLiked(false);
                courseCommentVO.setPraiseCount(0);
                return courseCommentVO;
            }
            courseCommentVO.setPraiseCount(praiseCount);

            //查询当前用户是否点赞
            String key = getLikedKey(userId, courseCommentId);
            Object likedObj = redisTemplate.opsForHash().get(COURSE_COMMENT_LIKE, key);
            if(likedObj == null) {
                courseCommentVO.setLiked(false);
                return courseCommentVO;
            }
            Integer liked = (Integer) likedObj;
            if(liked == null || liked == 0) {
                courseCommentVO.setLiked(false);
                return courseCommentVO;
            }
            courseCommentVO.setLiked(true);
            return courseCommentVO;
        }).collect(Collectors.toList());

        //封装对象并存总数
        CourseCommentListVO courseCommentListVO = new CourseCommentListVO();
        courseCommentListVO.setCourseCommentVOList(courseCommentVOList);
        courseCommentListVO.setTotalNum(total);

        return ResultUtils.success(courseCommentListVO);

    }
}




