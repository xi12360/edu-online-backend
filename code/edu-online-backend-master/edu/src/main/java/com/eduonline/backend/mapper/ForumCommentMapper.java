package com.eduonline.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eduonline.backend.model.entity.ForumComment;
import com.eduonline.backend.model.vo.course.CourseCommentVO;
import com.eduonline.backend.model.vo.forumComment.ForumCommentVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Acer
* @description 针对表【forum_comment(论坛评论)】的数据库操作Mapper
* @createDate 2023-08-25 16:59:15
* @Entity com.eduonline.backend.model.entity.ForumComment
*/
public interface ForumCommentMapper extends BaseMapper<ForumComment> {

    @Select("SELECT forum_comment.*, coalesce(student.user_name,teacher.name) as userName,coalesce(student.id,teacher.id) as userId, coalesce(student.pic_img,teacher.pic_path) as userImgUrl " +
            "FROM forum_comment " +
            "LEFT JOIN student ON forum_comment.user_id = student.id " +
            "LEFT JOIN teacher ON forum_comment.user_id = teacher.id " +
            "WHERE forum_comment.article_id = #{articleId} " +
            "AND forum_comment.p_comment_id IS NULL " +
            "AND forum_comment.is_delete = 0 " +
            "ORDER BY forum_comment.add_time DESC " +
            "LIMIT #{start}, #{pageSize}")
    List<ForumCommentVO> queryComments(String articleId, Integer start, Integer pageSize);

    @Select("SELECT forum_comment.*, coalesce(student.user_name,teacher.name) as userName,coalesce(student.id,teacher.id) as userId, coalesce(student.pic_img,teacher.pic_path) as userImgUrl " +
            "FROM forum_comment " +
            "LEFT JOIN student ON forum_comment.user_id = student.id " +
            "LEFT JOIN teacher ON forum_comment.user_id = teacher.id " +
            "WHERE forum_comment.p_comment_id = #{commentId} " +
            "AND forum_comment.is_delete = 0 " +
            "ORDER BY forum_comment.add_time DESC ")
    List<ForumCommentVO> queryChildComments(String commentId);


    @Select("SELECT COUNT(*) as total " +
            "FROM forum_comment " +
            "LEFT JOIN student ON forum_comment.user_id = student.id " +
            "LEFT JOIN teacher ON forum_comment.user_id = teacher.id " +
            "WHERE forum_comment.article_id = #{articleId} " +
            "AND forum_comment.is_delete = 0 " )
    Long queryTotal(String articleId);
}




