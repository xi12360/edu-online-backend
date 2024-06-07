package com.eduonline.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eduonline.backend.common.BaseResponse;
import com.eduonline.backend.model.dto.forum.ForumCommentRequest;
import com.eduonline.backend.model.vo.forumComment.ForumCommentListVO;
import com.eduonline.backend.model.dto.forum.ReplyForumCommentRequest;
import com.eduonline.backend.model.entity.ForumComment;

import javax.servlet.http.HttpServletRequest;

/**
* @author Acer
* @description 针对表【forum_comment(论坛评论)】的数据库操作Service
* @createDate 2023-08-25 16:59:15
*/
public interface ForumCommentService extends IService<ForumComment> {

    BaseResponse<ForumCommentListVO> queryComment(String articleId, String userId, Integer current, Integer pageSize, HttpServletRequest request);

    BaseResponse<Integer> comment(ForumCommentRequest forumCommentRequest, HttpServletRequest request);

    BaseResponse<Integer> replyComment(ReplyForumCommentRequest replyCommentRequest, HttpServletRequest request);

    BaseResponse<Integer> delComment(String commentId, String userId, HttpServletRequest request);

    BaseResponse<Integer> likeComment(String commentId, String userId, HttpServletRequest request);

    BaseResponse<ForumCommentListVO> queryChildComment(String commentId, String userId, HttpServletRequest request);
}
