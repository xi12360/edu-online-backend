package com.eduonline.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eduonline.backend.model.entity.CourseComment;
import com.eduonline.backend.model.vo.course.CourseCommentVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Acer
 * @description 针对表【course_comment】的数据库操作Mapper
 * @createDate 2023-08-25 16:59:15
 * @Entity com.eduonline.backend.model.entity.CourseComment
 */
public interface CourseCommentMapper extends BaseMapper<CourseComment> {

    @Select("SELECT course_comment.*, coalesce(student.user_name,teacher.name) as userName,coalesce(student.id,teacher.id) as userId, coalesce(student.pic_img,teacher.pic_path) as userImgUrl " +
            "FROM course_comment " +
            "LEFT JOIN student ON course_comment.user_id = student.id " +
            "LEFT JOIN teacher ON course_comment.user_id = teacher.id " +
            "WHERE course_comment.course_id = #{courseId} " +
            "AND course_comment.p_comment_id IS NULL " +
            "AND course_comment.is_delete = 0 " +
            "ORDER BY course_comment.add_time DESC " +
            "LIMIT #{start}, #{pageSize}")
    List<CourseCommentVO> queryComments(String courseId, Integer start, Integer pageSize);


    @Select("SELECT course_comment.*, coalesce(student.user_name,teacher.name) as userName,coalesce(student.id,teacher.id) as userId, coalesce(student.pic_img,teacher.pic_path) as userImgUrl " +
            "FROM course_comment " +
            "LEFT JOIN student ON course_comment.user_id = student.id " +
            "LEFT JOIN teacher ON course_comment.user_id = teacher.id " +
            "WHERE course_comment.p_comment_id = #{commentId} " +
            "AND course_comment.is_delete = 0 " +
            "ORDER BY course_comment.add_time DESC ")
    List<CourseCommentVO> queryChildComments(String commentId);

    @Select("SELECT COUNT(*) as total " +
            "FROM course_comment " +
            "LEFT JOIN student ON course_comment.user_id = student.id " +
            "LEFT JOIN teacher ON course_comment.user_id = teacher.id " +
            "WHERE course_comment.course_id = #{courseId}" +
            "AND course_comment.is_delete = 0 ")
    Long queryTotal(String courseId);
}




