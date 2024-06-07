package com.eduonline.backend.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.common.ErrorCode;
import com.eduonline.backend.common.ResultUtils;
import com.eduonline.backend.exception.BusinessException;
import com.eduonline.backend.mapper.AdminMapper;
import com.eduonline.backend.model.dto.forum.ForumCommentRequest;
import com.eduonline.backend.model.entity.Admin;
import com.eduonline.backend.model.vo.forumComment.ForumCommentListVO;
import com.eduonline.backend.model.dto.forum.ReplyForumCommentRequest;
import com.eduonline.backend.model.vo.forumComment.ForumCommentVO;
import com.eduonline.backend.service.ForumCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eduonline.backend.model.entity.ForumComment;
import com.eduonline.backend.mapper.ForumCommentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import static com.eduonline.backend.constant.ArticleConstant.FORUM_COMMENT_LIKE;
import static com.eduonline.backend.constant.ArticleConstant.FORUM_COMMENT_LIKE_COUNT;
import static com.eduonline.backend.constant.CommentConstant.COURSE_COMMENT_LIKE;
import static com.eduonline.backend.constant.CommentConstant.COURSE_COMMENT_LIKE_COUNT;
import static com.eduonline.backend.constant.UserConstant.ADMIN_ROLE;
import static com.eduonline.backend.service.impl.ArticleServiceImpl.getLikedKey;

/**
 * @author Acer
 * @description 针对表【forum_comment(论坛评论)】的数据库操作Service实现
 * @createDate 2023-08-25 16:59:15
 */
@Service
public class ForumCommentServiceImpl extends ServiceImpl<ForumCommentMapper, ForumComment>
        implements ForumCommentService {

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Resource
    private ForumCommentMapper forumCommentMapper;
    @Resource
    private AdminMapper adminMapper;

    /**
     * 分页查询评论
     *
     * @param userId
     * @param current
     * @param pageSize
     * @param request
     * @return
     */
    // todo
    @Override
    public BaseResponse<ForumCommentListVO> queryComment(String articleId, String userId, Integer current, Integer pageSize, HttpServletRequest request) {
        if(articleId == null || articleId.equals("")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "id不应为空");
        }
        List<ForumCommentVO> forumCommentVOList;
        long total;
        try {
            forumCommentVOList = forumCommentMapper.queryComments(articleId, (current - 1) * pageSize, pageSize);
            total = forumCommentMapper.queryTotal(articleId);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "mapper操作出现问题");
        }
        forumCommentVOList = forumCommentVOList.stream().map(forumCommentVO -> {
            //查询点赞总数
            String forumCommentId = forumCommentVO.getCommentId();
            Object countObj = redisTemplate.opsForHash().get(FORUM_COMMENT_LIKE_COUNT, forumCommentId);
            if(countObj == null) {
                forumCommentVO.setLiked(false);
                forumCommentVO.setPraiseCount(0);
                return forumCommentVO;
            }
            Integer praiseCount = (Integer) countObj;
            if(praiseCount == null) {
                forumCommentVO.setLiked(false);
                forumCommentVO.setPraiseCount(0);
                return forumCommentVO;
            }
            forumCommentVO.setPraiseCount(praiseCount);

            //查询当前用户是否点赞
            String key = getLikedKey(userId, forumCommentId);
            Object likedObj = redisTemplate.opsForHash().get(FORUM_COMMENT_LIKE, key);
            if(likedObj == null) {
                forumCommentVO.setLiked(false);
                return forumCommentVO;
            }
            Integer liked = (Integer) likedObj;
            if(liked == null || liked == 0) {
                forumCommentVO.setLiked(false);
                return forumCommentVO;
            }
            forumCommentVO.setLiked(true);
            return forumCommentVO;
        }).collect(Collectors.toList());

        //封装对象并存总数
        ForumCommentListVO forumCommentListVO = new ForumCommentListVO();
        forumCommentListVO.setForumCommentVOList(forumCommentVOList);
        forumCommentListVO.setTotal((int)total);

        return ResultUtils.success(forumCommentListVO);
    }

    /**
     * 发布评论
     *
     * @param forumCommentRequest
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> comment(ForumCommentRequest forumCommentRequest, HttpServletRequest request) {
        if (forumCommentRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不能为空");
        }
        String articleId = forumCommentRequest.getArticleId();
        String comment = forumCommentRequest.getComment();
        String userId = forumCommentRequest.getUserId();
        if (StringUtils.isAnyBlank(articleId, comment, userId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不能为空");
        }
        //保存评论
        ForumComment forumComment = new ForumComment();
        forumComment.setCommentId("forum_comment_" + RandomUtil.randomString(30));
        forumComment.setArticleId(articleId);
        forumComment.setUserId(userId);
        forumComment.setComment(comment);
        try {
            save(forumComment);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "操作数据库出现错误");
        }
        return ResultUtils.success(1);
    }

    /**
     * 回复评论
     *
     * @param replyCommentRequest
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> replyComment(ReplyForumCommentRequest replyCommentRequest, HttpServletRequest request) {
        if (replyCommentRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不得为空");
        }
        String pCommentId = replyCommentRequest.getCommentId();
        String comment = replyCommentRequest.getComment();
        String articleId = replyCommentRequest.getArticleId();
        String userId = replyCommentRequest.getUserId();
        String otherId = replyCommentRequest.getOtherId();
        if (StringUtils.isAnyBlank(pCommentId, comment, articleId, userId, otherId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不得为空");
        }
        ForumComment forumComment = new ForumComment();
        forumComment.setCommentId("forum_comment_" + RandomUtil.randomString(30));
        forumComment.setPCommentId(pCommentId);
        forumComment.setComment(comment);
        forumComment.setArticleId(articleId);
        forumComment.setOtherId(otherId);
        forumComment.setUserId(userId);
        try {
            save(forumComment);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "数据库操作失败");
        }
        return ResultUtils.success(1);
    }

    /**
     * 删除评论
     *
     * @param commentId
     * @param userId
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Integer> delComment(String commentId, String userId, HttpServletRequest request) {
        if (commentId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "id不能为空");
        }
        // 查询数据库,校验删除者是否是本人或管理员
        LambdaQueryWrapper<ForumComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ForumComment::getCommentId, commentId)
                .eq(ForumComment::getUserId, userId);
        long count = count(wrapper);
        if (count < 1) {
            //校验是否是管理员
            Admin admin = adminMapper.selectById(userId);
            if (admin == null) {
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
     *
     * @param commentId
     * @param userId
     * @param request
     * @return
     */
    // todo hash表：第一个参数为表名，第二个参数为标识key，第三个参数为点赞数或状态
    @Override
    public BaseResponse<Integer> likeComment(String commentId, String userId, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(commentId, userId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        // 用户id和评论id的相互联系 key为userId::commentId 值为0或1，代表点赞还是未点赞
        String key = getLikedKey(userId, commentId);
        //判断当前登录用户是否点赞
        Object status = redisTemplate.opsForHash().get(FORUM_COMMENT_LIKE, key);
        //用户未点赞
        if (status == null || (Integer) status == 0) { // 空值表示未记录点赞情况
            redisTemplate.opsForHash().put(FORUM_COMMENT_LIKE, key, 1);
            //点赞数+1
            redisTemplate.opsForHash().increment(FORUM_COMMENT_LIKE_COUNT, commentId, 1);
        }
        //用户已经点赞
        else if ((Integer) status == 1) {
            redisTemplate.opsForHash().put(FORUM_COMMENT_LIKE, key, 0);
            //点赞数-1
            redisTemplate.opsForHash().increment(FORUM_COMMENT_LIKE_COUNT, commentId, -1);
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "系统异常，点赞失败");
        }

        return ResultUtils.success(1);
    }

    @Override
    public BaseResponse<ForumCommentListVO> queryChildComment(String commentId, String userId, HttpServletRequest request) {
        if(commentId == null || commentId.equals("")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "id不应为空");
        }
        List<ForumCommentVO> forumCommentVOList;
        long total;
        try {
            forumCommentVOList = forumCommentMapper.queryChildComments(commentId);
            total = forumCommentVOList.size();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "mapper操作出现问题");
        }
        forumCommentVOList = forumCommentVOList.stream().map(forumCommentVO -> {
            //查询点赞总数
            String forumCommentId = forumCommentVO.getCommentId();
            Object countObj = redisTemplate.opsForHash().get(FORUM_COMMENT_LIKE_COUNT, forumCommentId);
            if(countObj == null) {
                forumCommentVO.setLiked(false);
                forumCommentVO.setPraiseCount(0);
                return forumCommentVO;
            }
            Integer praiseCount = (Integer) countObj;
            if(praiseCount == null) {
                forumCommentVO.setLiked(false);
                forumCommentVO.setPraiseCount(0);
                return forumCommentVO;
            }
            forumCommentVO.setPraiseCount(praiseCount);

            //查询当前用户是否点赞
            String key = getLikedKey(userId, forumCommentId);
            Object likedObj = redisTemplate.opsForHash().get(FORUM_COMMENT_LIKE, key);
            if(likedObj == null) {
                forumCommentVO.setLiked(false);
                return forumCommentVO;
            }
            Integer liked = (Integer) likedObj;
            if(liked == null || liked == 0) {
                forumCommentVO.setLiked(false);
                return forumCommentVO;
            }
            forumCommentVO.setLiked(true);
            return forumCommentVO;
        }).collect(Collectors.toList());

        //封装对象并存总数
        ForumCommentListVO forumCommentListVO = new ForumCommentListVO();
        forumCommentListVO.setForumCommentVOList(forumCommentVOList);
        forumCommentListVO.setTotal((int)total);

        return ResultUtils.success(forumCommentListVO);
    }


}




